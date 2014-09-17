package org.stackdroid.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View;

import org.stackdroid.R;
import org.stackdroid.utils.User;
import org.stackdroid.utils.Utils;

import java.io.File;

import org.stackdroid.views.UserView;
import org.stackdroid.utils.Configuration;
import org.stackdroid.utils.Defaults;
import org.stackdroid.utils.TextViewNamed;
import org.stackdroid.utils.ImageButtonNamed;

public class UsersActivity extends Activity {

  //__________________________________________________________________________________
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView( R.layout.users );
  }
  
  //__________________________________________________________________________________
  @Override
  public void onResume( ) {
    super.onResume( );
    refreshUserViews();
    if(Utils.getStringPreference("SELECTEDUSER","",this).length()==0) {
	Toast t = Toast.makeText(this, getString(R.string.TOUCHUSERTOSELECT), Toast.LENGTH_SHORT) ;
	t.show( );
    }
  }
  
  //__________________________________________________________________________________
  public void addUser( View v ) {
    Class<?> c = (Class<?>)UserAddActivity.class;
    Intent I = new Intent( UsersActivity.this, c );
    startActivity( I );  
  }

  protected class UserDeleteListener implements OnClickListener {
	  @Override
	  public void onClick( View v ) { 
		  String filenameToDelete = ((ImageButtonNamed)v).getUserView( ).getFilename();
			
			(new File(Configuration.getInstance().getValue("FILESDIR",Defaults.DEFAULTFILESDIR) + "/users/"+filenameToDelete)).delete();
			String selectedUser = Utils.getStringPreference("SELECTEDUSER", "", UsersActivity.this);
			if(selectedUser.compareTo(filenameToDelete)==0)
			    Utils.putStringPreference( "SELECTEDUSER", "", UsersActivity.this);
			
			refreshUserViews();
			return;
	  }
  }
  
/*  protected class UserModifyListener implements OnClickListener {
	  @Override
	  public void onClick( View v ) {
		  Utils.alert( getString(R.string.NOTIMPLEMENTED) , UsersActivity.this);
		  return;
	  }
  }
  */
  protected class UserSelectedListener implements OnClickListener {
	  @Override
	  public void onClick( View v ) {
		  Utils.putStringPreference("SELECTEDUSER", ((TextViewNamed)v).getUserView().getFilename(), UsersActivity.this);
		  refreshUserViews();
	  }
  }

    //__________________________________________________________________________________
    private void refreshUserViews( ) {
	File[] users = (new File( Configuration.getInstance().getValue("FILESDIR",Defaults.DEFAULTFILESDIR) + "/users/")).listFiles();
	if(users==null) {
	    Utils.alert("UsersActivity.refreshUserViews: " 
			+ Configuration.getInstance().getValue("FILESDIR",Defaults.DEFAULTFILESDIR) 
			+ "/users/" 
			+ " exists but it is not a directory !", this);
	    return;
	}
	    
	// TODO: should we filter here ?

	((LinearLayout)findViewById(R.id.userLayout)).removeAllViews();
	UserView lastUV = null;
	for(int i = 0; i<users.length; ++i) {
	    User U = null;
	    try {
		
	    	U = User.fromFileID( users[i].getName( ), Configuration.getInstance().getValue("FILESDIR",Defaults.DEFAULTFILESDIR) );
		
	    } catch(Exception e) {
	    	Utils.alert("ERROR: " + e.getMessage(), this);
	    	continue;
	    }
	    
	    UserView uv = new UserView ( U, new UsersActivity.UserDeleteListener(), new UsersActivity.UserSelectedListener(),this );
	    ((LinearLayout)findViewById(R.id.userLayout)).addView( uv );
	    View space = new View( this );
	    space.setMinimumHeight(10);
	    ((LinearLayout)findViewById(R.id.userLayout)).addView( space );
	    
	    if( uv.getFilename().compareTo(Utils.getStringPreference("SELECTEDUSER","",this))==0 )
	    	uv.setSelected( );
	    else
	    	uv.setUnselected( );
	    lastUV = uv;
	}
	if(users.length==1) {
		if(lastUV!=null) {
			lastUV.setSelected( );
			Utils.putStringPreference("SELECTEDUSER",lastUV.getFilename(),this);
		}
	}
	
    }
}
