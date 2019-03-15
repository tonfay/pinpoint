package com.navercorp.pinpoint.web.alarm.vo;

public class CheckerResult {
    
    private int historyId;
    private String applicationId;
    private String checkerName;
    private boolean detected;
    private int sequenceCount;
    private int timingCount;

    public CheckerResult() {
    }
    
    public CheckerResult(String applicationId, String checkerName, boolean detected, int sequenceCount, int timingCount) {
        this.applicationId = applicationId;
        this.checkerName = checkerName;
        this.detected = detected;
        this.sequenceCount = sequenceCount;
        this.timingCount = timingCount;
    }

    public int getHistoryId() {
        return historyId;
    }
    
    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getCheckerName() {
        return checkerName;
    }
    
    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }
    
    public boolean isDetected() {
        return detected;
    }
    
    public void setDetected(boolean detected) {
        this.detected = detected;
    }
    
    public int getSequenceCount() {
        return sequenceCount;
    }
    
    public void setSequenceCount(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }
    
    public int getTimingCount() {
        return timingCount;
    }
    
    public void setTimingCount(int timingCount) {
        this.timingCount = timingCount;
    }

    public void increseCount() {
//      timingCount = sequenceCount * 2 + 1;
        
//    timingCount = sequenceCount * 2 + 1;
      
	      //每次提醒后都在下一分钟再次提醒,源码规则是在当前次提醒后,下一次提醒时间是 * 2 + 1,  modify by tonfay
	      //老源码规则: 例: 1分钟提醒一次,1*2+1 = 3分钟提醒一次,3*2+1 7分钟提醒一次, 7*2+1 = 15分钟提醒一次,以此类推
	      timingCount = sequenceCount + 1;
    }
}
