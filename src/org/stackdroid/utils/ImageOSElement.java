package org.stackdroid.utils;

import android.widget.LinearLayout;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import org.stackdroid.views.UserView;

public class ImageOSElement {

    public LinearLayout row;
    public TextViewWithView view;
    public ImageViewWithView img;

    public ImageOSElement( String name, String format, long size, int imageResource, Context ctx ) {
	
	view = new TextViewWithView( ctx, (UserView)null );
	img  = new ImageViewWithView( ctx, (UserView)null );
	img.setClickable( true );
	img.setImageResource(imageResource);
	
	view.setText( name );
 	view.setTextSize(1, 20 );
	view.setPadding( 5, 4, 0, 0 );
 	view.setClickable( true );
	
 	row = new LinearLayout( ctx );
 	row.setOrientation( LinearLayout.HORIZONTAL );
 	
    }

    protected void add( ) {
	row.addView( img );
	row.addView( view );
	
	{
	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
	    params.gravity=Gravity.CENTER;
	    view.setLayoutParams( params ); 
	}
	
	ViewGroup.LayoutParams params = view.getLayoutParams();
	
	params.width = ViewGroup.LayoutParams.MATCH_PARENT;
	view.setLayoutParams( params );
    }
};
