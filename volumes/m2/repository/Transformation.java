
package com.upbusab.bureau.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sourceType",
    "resultType",
    "functionKey"
})
@Generated("jsonschema2pojo")
public class Transformation
    extends Entity
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceType")
    private String sourceType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultType")
    private String resultType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("functionKey")
    private String functionKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceType")
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sourceType")
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultType")
    public String getResultType() {
        return resultType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultType")
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("functionKey")
    public String getFunctionKey() {
        return functionKey;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("functionKey")
    public void setFunctionKey(String functionKey) {
        this.functionKey = functionKey;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Transformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        int baseLength = sb.length();
        String superString = super.toString();
        if (superString!= null) {
            int contentStart = superString.indexOf('[');
            int contentEnd = superString.lastIndexOf(']');
            if ((contentStart >= 0)&&(contentEnd >contentStart)) {
                sb.append(superString, (contentStart + 1), contentEnd);
            } else {
                sb.append(superString);
            }
        }
        if (sb.length()>baseLength) {
            sb.append(',');
        }
        sb.append("sourceType");
        sb.append('=');
        sb.append(((this.sourceType == null)?"<null>":this.sourceType));
        sb.append(',');
        sb.append("resultType");
        sb.append('=');
        sb.append(((this.resultType == null)?"<null>":this.resultType));
        sb.append(',');
        sb.append("functionKey");
        sb.append('=');
        sb.append(((this.functionKey == null)?"<null>":this.functionKey));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.functionKey == null)? 0 :this.functionKey.hashCode()));
        result = ((result* 31)+((this.sourceType == null)? 0 :this.sourceType.hashCode()));
        result = ((result* 31)+((this.resultType == null)? 0 :this.resultType.hashCode()));
        result = ((result* 31)+ super.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Transformation) == false) {
            return false;
        }
        Transformation rhs = ((Transformation) other);
        return ((((super.equals(rhs)&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.functionKey == rhs.functionKey)||((this.functionKey!= null)&&this.functionKey.equals(rhs.functionKey))))&&((this.sourceType == rhs.sourceType)||((this.sourceType!= null)&&this.sourceType.equals(rhs.sourceType))))&&((this.resultType == rhs.resultType)||((this.resultType!= null)&&this.resultType.equals(rhs.resultType))));
    }

}
