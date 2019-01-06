package com.yayandroid.locationmanager.sample
.secondpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import android.Manifest;

import com.yayandroid.locationmanager.sample.ExampleService;
import com.yayandroid.locationmanager.sample.R;
import com.yayandroid.locationmanager.sample.thirdpage.NewsGadgetsFull;
import com.yayandroid.locationmanager.sample.thirdpage.NewsSportsFull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.yayandroid.locationmanager.sample.R;
import com.yayandroid.locationmanager.sample.thirdpage.NewsGadgetsFull;
import com.yayandroid.locationmanager.sample.thirdpage.NewsEntertainmentFull;
import com.yayandroid.locationmanager.sample.Home;
import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class NewsEntertainment extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{
    String pass="123456789";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
        File imagepath;   Button up,down,share;
    TextView heading,shortdesc;

    public static  final int SWIPE_THRESHOLD=100;
    public static  final int SWIPE_VELOCITY_THRESHOLD=100;
    private GestureDetector gestureDetector;
    ViewFlipper viewFlipper;
    Dialog dialog,dialog1; ProgressDialog load;
    int i=1;     ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_entertainment);
		ImageView imageView=findViewById(R.id.button2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),Home.class);
                startActivity(a);
            }
        });
  
up=findViewById(R.id.up);   img=findViewById(R.id.slide); up.setOnClickListener(new View.OnClickListener() {            @Override  public void onClick(View v) {   up.setEnabled(false);  saveup();}});           down=findViewById(R.id.down);     down.setOnClickListener(new View.OnClickListener() {            @Override  public void onClick(View v) {  down.setEnabled(false);  savedown();}});      
        gestureDetector = new GestureDetector(this);
        load=new ProgressDialog(this);
	
		mAuth = FirebaseAuth.getInstance();Button tag = findViewById(R.id.tags);
		
		        share=findViewById(R.id.share);    share.setOnClickListener(new View.OnClickListener() { @Override      public void onClick(View v) {Bitmap bitmap=takescreen();  saveBitmap(bitmap);  shareit();  }});    tag.setOnClickListener(new View.OnClickListener() {
            @Override
		public void onClick(View v) {
		//CharSequence options[] = new CharSequence[]{"Sports", "Politics","Education","Entertainment","Lifestyle","Gadgets","Agriculture","Business",};
	      makedialog();
			
		}});
		
        //Toast.makeText(getApplicationContext(),"Entertainment News Here",//Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Double Tap to read complete news",//Toast.LENGTH_SHORT).show();
        heading=findViewById(R.id.heading);
        shortdesc=findViewById(R.id.desc);
                
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //Toast.makeText(getApplicationContext(), "Registering You!!", //Toast.LENGTH_LONG).show();
            dialog.setContentView(R.layout.instruction_dialog);
            dialog.show();}
        mAuth=FirebaseAuth.getInstance();

        getheading(); getimage(); 
        getshortdesc();
         

    }
	private void left() {
        Intent a=new Intent(getApplicationContext(),NewsGadgets.class);startActivity(a);
        //overridePendingTransition(R.anim.slideintop,R.anim.slideoutdown);
        CustomIntent.customType(this,"right-to-left");
    }
    private void right() {
        ////Toast.makeText(getApplicationContext(),"Top swipe",//Toast.LENGTH_SHORT).show();
        Intent a=new Intent(getApplicationContext(),NewsAgriculture.class);startActivity(a);
        //overridePendingTransition(R.anim.slideintop,R.anim.slideoutdown);
		CustomIntent.customType(this,"left-to-right");
    }
	private Bitmap takescreen(){
			View root=findViewById(android.R.id.content).getRootView();
			root.setDrawingCacheEnabled(true);
			return root.getDrawingCache();
			
		}	
				
		public void saveBitmap(Bitmap bitmap){
			  imagepath=new File(Environment.getExternalStorageDirectory() +"/screenshot.png");
			FileOutputStream fos;
			String path;
			//File file=new File(path);
			try{
				fos=new FileOutputStream(imagepath);
				bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
				fos.flush();
				fos.close();
			}catch(FileNotFoundException e){
			}
			catch(IOException e){}
			/*path=imagepath.getPath();
			Uri bmp=FileProvider.getUriForFile(getBaseContext(),getApplicationContext().getPackageName()+".provider",imagepath);
            Intent shareit=new Intent();
            shareit.setAction(Intent.ACTION_SEND);
            shareit.setType("image/png");
            shareit.putExtra(Intent.EXTRA_TEXT,shortdesc.getText());
            shareit.putExtra(Intent.EXTRA_STREAM,bmp);
            startActivity(shareit);
            */
        }
    public void shareit(){
        Uri path=FileProvider.getUriForFile(getBaseContext(),"com.yayandroid.locationmanager.sample",imagepath);
        Intent share=new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT,heading.getText());
        share.putExtra(Intent.EXTRA_STREAM,path);
        share.setType("image/*");
        startActivity(Intent.createChooser(share,"Share..."));

    }



  
    private void getshortdescr() {
        String in=i+"";
        DatabaseReference mshortdesc = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("content").child("shortdesc");
        mshortdesc.keepSynced(true);

        mshortdesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{String value = dataSnapshot.getValue(String.class);
                    shortdesc.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void getheadingr() {
        load.setTitle("Wait");
        load.setMessage("Getting the latest news for you..");
        load.show();
        String in=i+"";
        DatabaseReference mheading = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("content").child("heading");
// Read from the database
        mheading.keepSynced(true);
        mheading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                load.dismiss();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){right();}
                else{String value = dataSnapshot.getValue(String.class);
                    heading.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
   
    private void getimagel() {
        String in=i+"";

        DatabaseReference mimage = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("pic").child("id");
        mimage.keepSynced(true);
        mimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){
					
				}
                else{
                    final  String image1=dataSnapshot.getValue().toString();
                    Picasso.get().load(image1).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.slide1).into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image1).placeholder(R.drawable.slide1).into(img);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
	
	
    private void getshortdescl() {
        String in=i+"";
        DatabaseReference mshortdesc = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("content").child("shortdesc");
        mshortdesc.keepSynced(true);

        mshortdesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{String value = dataSnapshot.getValue(String.class);
                    shortdesc.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void getheadingl() {
        load.setTitle("Wait");
        load.setMessage("Getting the latest news for you..");
        load.show();
        String in=i+"";
        DatabaseReference mheading = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("content").child("heading");
// Read from the database
        mheading.keepSynced(true);
        mheading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                load.dismiss();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){left();}
                else{String value = dataSnapshot.getValue(String.class);
                    heading.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
   

    private void getshortdesc() {
        String in=i+"";
        DatabaseReference mshortdesc = FirebaseDatabase.getInstance().getReference().child("Entertainment").child(in).child("content").child("shortdesc");
                  mshortdesc.keepSynced(true);  mshortdesc.keepSynced(true);  mshortdesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {                load.dismiss();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{String value = dataSnapshot.getValue(String.class);
                shortdesc.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void getimage() {
        String in=i+"";

        DatabaseReference mimage = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("pic").child("id");

        mimage.keepSynced(true);    mimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{
                    final  String image1=dataSnapshot.getValue().toString();
                    Picasso.get().load(image1).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.slide1).into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image1).placeholder(R.drawable.slide1).into(img);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
	    private void saveup(){        String in=i+"";
		                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                   DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Upvoted").child("Entertainment").child(in);
				   mDatabase.keepSynced(true);  mDatabase.setValue("UP VOTED").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getApplicationContext(), "UpVoted!!", //Toast.LENGTH_LONG).show();
                                finish();

                            }

                        }
                    });


                } 
		private void savedown(){        String in=i+"";
		
		                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                   DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Downvoted").child("Entertainment").child(in);
				   mDatabase.keepSynced(true); mDatabase.setValue("Down VOTED").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getApplicationContext(), "Down Voted!!", //Toast.LENGTH_LONG).show();
                                finish();

                            }

                        }
                    });


                }         private void makedialog(){
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewsEntertainment.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_option, null);
				TextView sports,politics,education,entertainment,lifestyle,gadgets,agriculture,business,international;
            sports=mView.findViewById(R.id.sports);
            politics=mView.findViewById(R.id.politics);
            education=mView.findViewById(R.id.education);
            entertainment=mView.findViewById(R.id.entertainment);
            lifestyle=mView.findViewById(R.id.lifestyle);
            gadgets=mView.findViewById(R.id.gadget);
            agriculture=mView.findViewById(R.id.agriculture);
            business=mView.findViewById(R.id.business);
            international=mView.findViewById(R.id.international);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsSports.class);
						startActivity(a);
					}});
					
					politics.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsPolitics.class);
						startActivity(a);
					}});
					
					education.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsEducation.class);
						startActivity(a);
					}});
					
					entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsEntertainment.class);
						startActivity(a);
					}});
					
					lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsLifestyle.class);
						startActivity(a);
					}});
					gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsGadgets.class);
						startActivity(a);
					}});
					agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsAgriculture.class);
						startActivity(a);
					}});
					
					business.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsBusiness.class);
						startActivity(a);
					}});
					international.setOnClickListener(new View.OnClickListener() {
            @Override
					public void onClick(View v) {
						Intent a=new Intent(getApplicationContext(),NewsInternational.class);
						startActivity(a);
					}});
	}
    private void getheading() {        load.setTitle("Wait");   load.setMessage("Getting the latest news for you.."); load.show();
        String in=i+"";
        DatabaseReference mheading = FirebaseDatabase.getInstance().getReference().child("Entertainment").child(in).child("content").child("heading");
// Read from the database
                  mheading.keepSynced(true);  mheading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {                load.dismiss();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{String value = dataSnapshot.getValue(String.class);
                heading.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

     

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downevent, MotionEvent moveevent, float velocityX, float velocityY) {
        boolean result=false;
        float diffY=moveevent.getY() - downevent.getY();
        float diffX=moveevent.getX() - downevent.getX();
        if(Math.abs(diffX)>Math.abs(diffY)){
            //right or left swipe
            result=true;
            if(Math.abs(diffX)>SWIPE_THRESHOLD && Math.abs(velocityX)>SWIPE_VELOCITY_THRESHOLD ){
                if(diffX>0){onSwipeRight();}
                else {onSwipeLeft();}

            }

        }
        else{
            //up or down swipe
            result=true;
            if(Math.abs(diffY)>SWIPE_THRESHOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD){
                if(diffY>0){onSwipeBottom();}
                else{onSwipeTop();}
            }
        }

        return result;
    }
 private void getimager() {
        String in=i+"";

        DatabaseReference mimage = FirebaseDatabase.getInstance().getReference().child("Sports").child(in).child("pic").child("id");
        mimage.keepSynced(true);
        mimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){
					
				}
                else{
                    final  String image1=dataSnapshot.getValue().toString();
                    Picasso.get().load(image1).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.slide1).into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image1).placeholder(R.drawable.slide1).into(img);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
	
    private void onSwipeRight() {
        ////Toast.makeText(getApplicationContext(),"Right swipe",//Toast.LENGTH_SHORT).show();
        i--;
        getheadingr(); getimager(); 
        getshortdescr();
    }
    private void onSwipeLeft() {
        ////Toast.makeText(getApplicationContext(),"Right swipe",//Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Sub Topic",//Toast.LENGTH_SHORT).show();
            i++;
            getheadingl(); getimagel();
            getshortdescl();
        }


    private void onSwipeTop() {
        Intent a=new Intent(getApplicationContext(),NewsGadgets.class);startActivity(a);
        CustomIntent.customType(this,"bottom-to-up");
    }
    private void onSwipeBottom() {
        ////Toast.makeText(getApplicationContext(),"Top swipe",//Toast.LENGTH_SHORT).show();
        Intent a=new Intent(getApplicationContext(),NewsAgriculture.class);startActivity(a);
		CustomIntent.customType(this,"up-to-bottom");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Intent a = new Intent(getApplicationContext(),NewsEntertainmentFull.class);
        a.putExtra("k",i);
        startActivity(a);
        CustomIntent.customType(getApplicationContext(),"left-to-right");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        return false;
    }
}
