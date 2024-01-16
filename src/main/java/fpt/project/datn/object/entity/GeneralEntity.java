package fpt.project.datn.object.entity;

import fpt.project.datn.utility.Utility;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class GeneralEntity {
    @CreatedBy
    @Column(updatable = false)
    private String createBy;
    @CreatedDate
    @Column(updatable = false)
    private Date createAt;
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updateAt;
    @Column(insertable = false)
    private boolean isDeleted;
    @Column(insertable = false)
    private String deleteBy;
    @Column(insertable = false)
    private Date deleteAt;
    
    @PreUpdate
    public void onUpdate() {
        if(this.isDeleted) {
            this.deleteBy = Utility.getCurrentUserName();
            this.deleteAt = new Date();
        } else {
            this.deleteBy = null;
            this.deleteAt = null;
        }
    }
}
