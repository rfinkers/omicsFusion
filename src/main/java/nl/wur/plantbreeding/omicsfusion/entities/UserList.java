/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Richard Finkers
 */
@Entity
@Table(name = "userList", catalog = "omicsFusion", schema = "")
@NamedQueries({
    @NamedQuery(name = "UserList.findAll", query = "SELECT u FROM UserList u"),
    @NamedQuery(name = "UserList.findById",
    query = "SELECT u FROM UserList u WHERE u.id = :id"),
    @NamedQuery(name = "UserList.findByUserName",
    query = "SELECT u FROM UserList u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserList.findByEmail",
    query = "SELECT u FROM UserList u WHERE u.email = :email"),
    @NamedQuery(name = "UserList.findByAffiliation",
    query = "SELECT u FROM UserList u WHERE u.affiliation = :affiliation"),
    @NamedQuery(name = "UserList.findByCountry",
    query = "SELECT u FROM UserList u WHERE u.country = :country"),
    @NamedQuery(name = "UserList.findByDateCreated",
    query = "SELECT u FROM UserList u WHERE u.dateCreated = :dateCreated"),
    @NamedQuery(name = "UserList.findByLastUpdate",
    query = "SELECT u FROM UserList u WHERE u.lastUpdate = :lastUpdate")})
public class UserList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "userName", nullable = false, length = 75)
    private String userName;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 75)
    private String email;
    @Column(name = "affiliation", length = 75)
    private String affiliation;
    @Column(name = "country", length = 75)
    private String country;
    @Basic(optional = false)
    @Column(name = "dateCreated", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "lastUpdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public UserList() {
    }

    public UserList(Integer id) {
        this.id = id;
    }

    public UserList(Integer id, String userName, String email,
            Date dateCreated, Date lastUpdate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.dateCreated = dateCreated;
        this.lastUpdate = lastUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( id != null ? id.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id
        // fields are not set
        if (!( object instanceof UserList )) {
            return false;
        }
        UserList other = (UserList) object;
        if (( this.id == null && other.id != null )
                || ( this.id != null && !this.id.equals(other.id) )) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.wur.plantbreeding.omicsfusion.entities.UserList[id=" + id + "]";
    }
}
