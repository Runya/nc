package entity;

import javax.persistence.*;

/**
 * Created by Ruslan on 19.03.2015.
 */
@Entity
@Table(name = "TEST_TABLE", schema = "LAB4", catalog = "")
public class TestTableEntity {
    private long id;
    private String str;

    public TestTableEntity(){}

    public TestTableEntity(String s) {
        setStr(s);
    }

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "STR")
    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTableEntity that = (TestTableEntity) o;

        if (id != that.id) return false;
        if (str != null ? !str.equals(that.str) : that.str != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (str != null ? str.hashCode() : 0);
        return result;
    }
}
