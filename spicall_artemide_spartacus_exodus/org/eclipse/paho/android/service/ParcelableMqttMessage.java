package org.eclipse.paho.android.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ParcelableMqttMessage
  extends MqttMessage
  implements Parcelable
{
  public static final Parcelable.Creator<ParcelableMqttMessage> CREATOR = new Parcelable.Creator()
  {
    public ParcelableMqttMessage createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ParcelableMqttMessage(paramAnonymousParcel);
    }
    
    public ParcelableMqttMessage[] newArray(int paramAnonymousInt)
    {
      return new ParcelableMqttMessage[paramAnonymousInt];
    }
  };
  String messageId = null;
  
  ParcelableMqttMessage(Parcel paramParcel)
  {
    super(paramParcel.createByteArray());
    setQos(paramParcel.readInt());
    boolean[] arrayOfBoolean = paramParcel.createBooleanArray();
    setRetained(arrayOfBoolean[0]);
    setDuplicate(arrayOfBoolean[1]);
    this.messageId = paramParcel.readString();
  }
  
  ParcelableMqttMessage(MqttMessage paramMqttMessage)
  {
    super(paramMqttMessage.getPayload());
    setQos(paramMqttMessage.getQos());
    setRetained(paramMqttMessage.isRetained());
    setDuplicate(paramMqttMessage.isDuplicate());
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getMessageId()
  {
    return this.messageId;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeByteArray(getPayload());
    paramParcel.writeInt(getQos());
    paramParcel.writeBooleanArray(new boolean[] { isRetained(), isDuplicate() });
    paramParcel.writeString(this.messageId);
  }
}


/* Location:              ~/org/eclipse/paho/android/service/ParcelableMqttMessage.class
 *
 * Reversed by:           J
 */