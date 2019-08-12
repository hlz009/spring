package com.xz.webdemo.mode;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共基类，存放共有字段
 * @MappedSupperclass
 * @author xiaozhi009
 * 其余注释都是lombok，用于自动生成setter Or getter，custructor
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
/** 增加了jackson-datatype-hibernate5就不需要这个Ignore*/
@JsonIgnoreProperties(value= {"hibernateLazyInitializer"})
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1440696945507847491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false)
	@CreationTimestamp
	private Date createTime;
	@UpdateTimestamp
	private Date updateTime;
}
