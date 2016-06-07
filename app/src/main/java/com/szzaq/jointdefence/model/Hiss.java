package com.szzaq.jointdefence.model;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class Hiss {
    public String date, reviewer, report, remark, patrolmen, supervisor, patrol_times, account, url, id;//户籍化账号，从config中读取，调试接口时可以写死

    /**
     * @param date
     * @param patrolmen//巡查员
     * @param reviewer//检查人
     * @param remark//备注
     * @param report//总体情况
     * @param supervisor//主管
     * @param patrol_times//巡查次数
     */
    public Hiss(String date, String patrolmen, String reviewer, String remark, String report, String supervisor, String patrol_times) {
        this.date = date;
        this.patrolmen = patrolmen;
        this.reviewer = reviewer;
        this.remark = remark;
        this.report = report;
        this.supervisor = supervisor;
        this.patrol_times = patrol_times;
    }
}
