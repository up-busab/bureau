boolean isProd = false

String webpackEnv = isProd ? "prod" : "dev" 
String webpackConfig = "./build/webpack." + webpackEnv + ".conf.js"

String buildDir = ""
String nodeDir = buildDir + "node/node"
String moduleDir = buildDir + "node_modules"

String wpDir = moduleDir + "/webpack/bin/webpack.js"
String wpdevDir = moduleDir + "/webpack-dev-server/bin/webpack-dev-server.js"

List<String> goals = session.getGoals()

println "Compiling client code..."

def webpack = new ProcessBuilder([nodeDir, wpDir, "--config="+webpackConfig, 
                                   "--display-error-details",
                                   "-p", "--progress", "--bail"
                                 ])
                                   .inheritIO()
                                   .directory(project.getBasedir())

def env = webpack.environment()
env.put("WAR_NAME", project.build.finalName)
def proc_webpack = webpack.start()
proc_webpack.waitForOrKill(120000)
if(proc_webpack.exitValue() != 0)
        throw new org.apache.maven.plugin.MojoFailureException("Error compiling client code")
