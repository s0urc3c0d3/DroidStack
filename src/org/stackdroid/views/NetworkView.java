package org.stackdroid.views;


import org.stackdroid.utils.Network;
import android.widget.CheckBox;
import android.content.Context;
//import android.graphics.Color;

public class NetworkView extends CheckBox {
    
    //private Context ctx = null;
    Network net = null;

    public NetworkView( Network net, OnClickListener listener, Context ctx ) {
	  super(ctx);
	  setOnClickListener(listener);
	  setText( net.getName( )+" ("+net.getSubNetworks()[0].getAddress()+")" );
	  this.net = net;
    }
    
    public Network getNetwork( ) { return net; }
    
}
