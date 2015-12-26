package com.yumait.clashofcapitalists.models.interfaces;

import com.yumait.clashofcapitalists.models.Action;

import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public interface IUser {

    void connect(String token, ICallBack callBack);
    void getData(String token, ICallBack callBack);

    void sendAction(Action action, ICallBack callBack);


}
