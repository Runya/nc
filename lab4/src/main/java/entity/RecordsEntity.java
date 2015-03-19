package entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Ruslan on 19.03.2015.
 */
@Entity
@Table(name = "RECORDS", schema = "LAB4", catalog = "")
public class RecordsEntity {
    private long id;
    private Timestamp data;
    private String importance;
    private String source;
    private String message;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATA")
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Basic
    @Column(name = "IMPORTANCE")
    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    @Basic
    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordsEntity that = (RecordsEntity) o;

        if (id != that.id) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (importance != null ? !importance.equals(that.importance) : that.importance != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (importance != null ? importance.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
