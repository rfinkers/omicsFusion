/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.datatypes;

/**
 *
 * @author finke002
 */
public class OntologyTraitDataType {
    /** Name of the variable. */
    private String variableName;
    /** Name of the ontology identifier. */
    private String ontologyID;

    public OntologyTraitDataType() {
    }

    public OntologyTraitDataType(String variableName, String ontologyID) {
        this.variableName = variableName;
        this.ontologyID = ontologyID;
    }

    public String getOntologyID() {
        return ontologyID;
    }

    public void setOntologyID(String ontologyID) {
        this.ontologyID = ontologyID;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "OntologyTraitDataType{" + "variableName=" + variableName + ", ontologyID=" + ontologyID + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OntologyTraitDataType other = (OntologyTraitDataType) obj;
        if (( this.variableName == null ) ? ( other.variableName != null ) : !this.variableName.equals(other.variableName)) {
            return false;
        }
        if (( this.ontologyID == null ) ? ( other.ontologyID != null ) : !this.ontologyID.equals(other.ontologyID)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + ( this.variableName != null ? this.variableName.hashCode() : 0 );
        hash = 97 * hash + ( this.ontologyID != null ? this.ontologyID.hashCode() : 0 );
        return hash;
    }

    

}
