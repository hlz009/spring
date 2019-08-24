package com.xz.hateoascustom.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共基类，存放共有字段
 * @MappedSupperclass
 * @author xiaozhi009
 * 其余注释都是lombok，用于自动生成setter Or getter，custructor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1440696945507847491L;

	private Long id;
	private Date createTime;
	private Date updateTime;
}
