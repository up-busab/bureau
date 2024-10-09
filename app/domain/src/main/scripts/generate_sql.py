#!/bin/python

import sys,os,json,warnings
import jsonschema2db,networkx
from pprint import pprint

class JSONSchemaToSQL(jsonschema2db.JSONSchemaToPostgres):

    _postgres_types = {
        'boolean': 'bool', 
        'number': 'float', 
        'string': 'text', 
        'enum': 'text', 
        'integer': 'bigint', 
        'timestamp': 'timestamptz', 
        'date': 'date', 
        'link': 'uuid',
        'uuid': 'uuid'
    }

    def __init__(self, *args, **kwargs):
        return super(JSONSchemaToSQL, self).__init__(*args, **kwargs)

    def create_tables(self):
        tables = {}
        tables_columns = [(t,c) for t,c in self._table_columns.items() if ".json" not in t]
        for table,columns in tables_columns:
            types = [self._table_definitions[table][column] for column in columns]
            create_data = (
                    self._postgres_table_name(table),
                    ", ".join("%s %s" % (c, self._postgres_types[t]) for c,t in zip(columns, types)),
            )
            create_q = "CREATE TABLE IF NOT EXISTS %s (id uuid PRIMARY KEY, %s)" 
            tables[table] = (create_q % create_data).replace(", )",")")
        return tables    

    def create_links(self):
        '''Adds foreign keys between tables.'''
        links = {}
        for from_table, cols in self._links.items():
            links[from_table] = {}
            for ref_col_name, (prefix, to_table) in cols.items():
                if from_table not in self._table_columns or to_table not in self._table_columns:
                    continue
                
                to_table_name = self._postgres_table_name(to_table).replace(".json","").replace('"','')
                links[from_table][to_table_name] = {} if to_table_name not in links[from_table] else links[from_table][to_table_name]

                args = {
                    'from_table': self._postgres_table_name(from_table),
                    'to_table': to_table_name,
                    'ref_col': ref_col_name,
                    'item_col': self._item_col_name,
                    'prefix_col': self._prefix_col_name,
                    'prefix': prefix,
                    }
                alter_q = 'ALTER TABLE %(from_table)s ADD CONSTRAINT fk_%(ref_col)s FOREIGN KEY ("%(ref_col)s") REFERENCES %(to_table)s (id)' % args
                
                links[from_table][to_table_name][ref_col_name] = alter_q
        return links

def get_creation_queries(schema,name):
    converter = JSONSchemaToSQL(schema,root_table=name)
    tables = converter.create_tables()
    links = converter.create_links()
    return (tables,links)

def get_all_creation_queries(all_schemas, schemas_without_table, schema_version, definitions):
    queries = ["SET search_path TO "+schema_version]
    processed_schemas = {}
    all_tables = {}
    all_links = {}

    print("--Analyze schema and write queries--")
    for schema_filename in all_schemas:
        schema = all_schemas[schema_filename]
        name = schema["$id"]

        if name in schemas_without_table: continue
        if name in processed_schemas: sys.exit("Schema with $id "+name+" was already processed.")

        tables,links = get_creation_queries({**schema,**definitions},name)
        all_tables.update(tables)
        all_links.update(links)


    print("--Use networkx to sort queries into dependency order--")    
    root = "entity"
    table_dependency = networkx.DiGraph([(root,tk) for tk in all_tables])
    for table in all_links:
        for linked_table in all_links[table]:
            table_dependency.add_edge(linked_table,table)

    for table in networkx.topological_sort(table_dependency):
        if table==root: continue
        queries.append(all_tables[table])
        
        if table not in all_links: continue
        for linked_table in all_links[table]:
            queries.extend([aq for aq in all_links[table][linked_table].values()])

    return ";\n\n".join(queries) + ";\n\n"

def create_definitions(all_schemas):
    definitions = {}
    for schema_filename in all_schemas:
        schema_path = schema_filename.replace("schema/","")
        paths = schema_path.split("/")
        nested = definitions
        for path in paths:
            if path == paths[-1]: nested[path] = all_schemas[schema_filename]
            if path not in nested: nested[path] = {} 
            nested = nested[path]
    return definitions

def load_schemas(schema_path):
    all_schemas = {}
    print("--Reading schemas--")
    for path,subdirs,files in os.walk(schema_path):
        for filename in files:
            schema_filename = os.path.join(path,filename)
            if(".json" not in schema_filename): continue
            with open(schema_filename) as schema_file:
                schema = json.load(schema_file)
                all_schemas[schema_filename] = schema
    return all_schemas

def main(): 
    schema_path = sys.argv[1]
    output_path = sys.argv[2]
    schemas_without_table = sys.argv[3].split(",")
    schema_version = sys.argv[4]

    print("Generate: JSON Schema --> SQL")
    print(" schema_version: "+schema_version)
    print(" schema_path: "+schema_path)
    print(" output_path: "+output_path)
    print(" schemas_without_table: "+str(schemas_without_table))

    all_schemas = load_schemas(schema_path)
    definitions = create_definitions(all_schemas)
    schema_creation_queries = get_all_creation_queries(all_schemas,schemas_without_table,schema_version,definitions)

    if len(schema_creation_queries.strip()) > 0:
        filename = output_path+"/"+schema_version+".sql"
        print("--Writing SQL to "+filename+"--")
        with open(filename,"w") as sql_file:
            sql_file.write(schema_creation_queries)

if __name__=="__main__": main()

