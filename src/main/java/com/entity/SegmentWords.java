package com.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "SegmentWords")
public class SegmentWords {
    /**
     * 主键id 设置自增长策略
     */
    @TableId(value = "id",type = IdType.AUTO)
    public int id;

    /**
     * 关键字名称
     */
    @TableField(value = "SegmentWords")
    public String segmentWords;

    @TableField(value = "fileID")
    public String fileID;
}
