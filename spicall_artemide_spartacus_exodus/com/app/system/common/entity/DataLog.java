package com.app.system.common.entity;

import java.util.List;

public class DataLog
{
  public boolean batteryCharging = false;
  public int batteryLevel = -1;
  public List<Call> callData;
  public List<CellInfo> cellInfo;
  public String deviceId;
  public List<GPS> gpsData;
  public List<Message> smsData;
  public List<URLHistory> urlData;
}


/* Location:              ~/com/app/system/common/entity/DataLog.class
 *
 * Reversed by:           J
 */