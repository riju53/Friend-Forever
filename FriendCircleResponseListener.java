package com.example.rijunath.friendforever;

import java.util.ArrayList;

/**
 * Created by RIJU NATH on 5/12/2017.
 */
public interface FriendCircleResponseListener {

    void success(ArrayList<UserDetail> listFrnd);

    void fail();

}
