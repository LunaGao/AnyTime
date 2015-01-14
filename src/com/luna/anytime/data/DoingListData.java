package com.luna.anytime.data;

import java.util.ArrayList;

public class DoingListData {

  public DoingListData() {
  }

  public DoingListData(String objectId, String DataTitle) {
    this.objectId = objectId;
    this.DataTitle = DataTitle;
  }

  public String objectId;
  public String DataTitle;
  public ArrayList<DoingListData> doingListData;

  @Override
  public String toString() {
    return DataTitle;
  }
}
