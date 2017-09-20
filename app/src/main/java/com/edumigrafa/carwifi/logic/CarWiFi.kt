package com.edumigrafa.carwifi.logic

import android.hardware.SensorEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.edumigrafa.carwifi.KotlinActivity
import com.edumigrafa.carwifi.R
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * Created by anderson on 13/09/17.
 */
class CarWiFi {

    var activity: KotlinActivity? = null

    constructor(activity: KotlinActivity) {
        this.activity = activity
    }

    var message: String = ""
    fun execHttp(data: String) {
        if (/*!activity!!.radio_button_gear_P.isChecked && */!message.equals(data)) {
            message = data
            var http: String = ADRESS_HTTP + data + "/"
            println(http)
            //activity!!.toast(http)
            Fuel.get(http).response { request, response, result ->
                //println(request)
                //println(response)
                //println(result)
                //val (bytes, error) = result
                //if (bytes != null) {
                //    println(bytes)
                //}

                //text.text("Result = " + result.success
                //+ "\n"
                //+ "Response = " + response.httpResponseMessage.toString()
                //+ "\n"
                //+ "Request = " + request.toString()
                //)
            }
        }
    }

    //@TODO Implementar vibração
    // act = true >> liga || act = false >> desliga
    var flashlight: Boolean = false
    fun flasherFlashlight(img: ImageView, act: Boolean) {
        if (flashlight == act && act) {return}

        val animation = AnimationUtils.loadAnimation(activity, R.anim.blink_2)

        if (act) {
            img.startAnimation(animation)
        } else {
            img.clearAnimation()
        }

        flashlight = act
    }

    var light_gyroflex: Boolean = false
    fun flasherGyroflex() {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.blink)

        if (!light_gyroflex) {
            activity!!.img_light_gyroflex.startAnimation(animation)
            execHttp(PIN_GYROFLEX + "1")
        } else {
            activity!!.img_light_gyroflex.clearAnimation()
            execHttp(PIN_GYROFLEX + "0")
        }

        light_gyroflex = light_gyroflex.not()
    }

    fun onOffBuzzer() {
        if (activity!!.switch_buzzer.isChecked) {
            execHttp(PIN_BUZZER + "1")
        } else {
            execHttp(PIN_BUZZER + "0")
        }
    }

    var car_headlight: Boolean = false
    fun onOffCarHeadlight() {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.blink)

        if (!car_headlight) {
            activity!!.img_button_car_headlight_left.startAnimation(animation)
            activity!!.img_button_car_headlight_right.startAnimation(animation)
            execHttp(PIN_CAR_HEADLIGHT + "1")
        } else {
            activity!!.img_button_car_headlight_left.clearAnimation()
            activity!!.img_button_car_headlight_right.clearAnimation()
            execHttp(PIN_CAR_HEADLIGHT + "0")
        }

        car_headlight = car_headlight.not()
    }

    // act = true >> ligar || act = false >> desligar
    fun actionExtras(pin: String, act: Boolean) {

        //@TODO Implementar no receptor
        //execHttp(PIN + "0")
    }

    fun actionFront(front: Int) {
        if (front == 0) {
            execHttp(PIN_DIRECTION_FRONT + "0")

            //activity!!.seekBar_accelerator.progress = 0
            //activity!!.seekBar_accelerator.isEnabled = false
        } else {
            execHttp(PIN_DIRECTION_FRONT + front.toString())
        }
    }

    fun actionBack(back: Int) {
        if (back == 0) {
            execHttp(PIN_DIRECTION_BACK + "0")
            //
        } else {
            execHttp(PIN_DIRECTION_BACK + back.toString())
        }
    }

    fun resetActionDirection() {
        execHttp(PIN_DIRECTION_LEFT + "0")
        execHttp(PIN_DIRECTION_RIGHT + "0")

        activity!!.imgLeft.visibility = View.INVISIBLE
        activity!!.imgRight.visibility = View.INVISIBLE

        flasherFlashlight(activity!!.img_flashelight_left, false)
        flasherFlashlight(activity!!.img_flashlight_right, false)
    }

    var stable: Boolean = true;
    fun actionDirection(event: SensorEvent?) {
        var float_ex: Float = event!!.values.get(0)
        var float_ey: Float = event!!.values.get(1)
        //println(float_ey)
        var float_ez: Float = event!!.values.get(2)

        var str: String = ""
        str += "Posição X: " + float_ex.toString()
        str += "\n"
        str += "Posição Y: " + float_ey.toString()
        str += "\n"
        str += "Posição Z: " + float_ez.toString()
        activity!!.text.setText(str);
        /*
        if(float_ey < 0) { // O dispositivo esta de cabeça pra baixo
            if(float_ex > 0)
                activity!!.text2.setText("Virando para ESQUERDA ficando INVERTIDO");
            if(float_ex < 0)
                activity!!.text2.setText("Virando para DIREITA ficando INVERTIDO");
        } else {
            if(float_ex > 0)
                activity!!.text2.setText("Virando para ESQUERDA ");
            if(float_ex < 0)
                activity!!.text2.setText("Virando para DIREITA ");
        }
        */
        activity!!.text2.text = float_ey.toString()

        if (float_ex < LIMIT_X_1 || float_ex > LIMIT_X_2 && !stable) {
            resetActionDirection()
            stable = true
            return
        }

        //activity!!.seekBar_accelerator.isEnabled = true

        if (float_ey > LIMITNEGATIVE_Y && float_ey < LIMITPOSITIVE_Y) {
            if (!stable) {
                resetActionDirection()
                stable = true
            }
        }

        if (float_ey > LIMITPOSITIVE_Y) {
            execHttp(PIN_DIRECTION_RIGHT + "1")
            activity!!.imgRight.visibility = View.VISIBLE
            flasherFlashlight(activity!!.img_flashlight_right, true)
            stable = false
        }

        if (float_ey < LIMITNEGATIVE_Y) {
            execHttp(PIN_DIRECTION_LEFT + "1")
            activity!!.imgLeft.visibility = View.VISIBLE
            flasherFlashlight(activity!!.img_flashelight_left, true)
            stable = false
        }
    }

/*


/* The following code was written by
 *
 *   Matthew Wiggins (Jun 2009)
 *
 * and is released under the APACHE 2.0 license
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.hlidskialf.android.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.lang.UnsupportedOperationException;
import java.util.List;

public class OrientationListener implements SensorEventListener
{
  public static final int ORIENTATION_UNDETERMINED=-1;
  public static final int ORIENTATION_NORTH=0;
  public static final int ORIENTATION_EAST=1;
  public static final int ORIENTATION_SOUTH=2;
  public static final int ORIENTATION_WEST=3;

  private SensorManager mSensorMgr;
  private Sensor mSensor;
  private Context mContext;
  private int mCurOrientation = ORIENTATION_UNDETERMINED;
  private OnRotateListener mRotateListener;

  public interface OnRotateListener
  {
    public void onRotate(int orientation);
  }


  public OrientationListener(Context context)
  {
    mContext = context;
  }

  public void resume() {
    mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
    if (mSensorMgr == null) {
      throw new UnsupportedOperationException("Sensors not supported");
    }

    List sensors = mSensorMgr.getSensorList(Sensor.TYPE_ORIENTATION);
    if (sensors.size() < 1)
      throw new UnsupportedOperationException("Orientation not supported");
    mSensor = sensors.get(0);
    if (!mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI))
      throw new UnsupportedOperationException("Orientation not supported");
  }
  public void pause() {
    if (mSensorMgr != null) {
      mSensorMgr.unregisterListener(this, mSensor);
      mSensorMgr = null;
    }
  }
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}
  public void onSensorChanged(SensorEvent event)
  {
    int new_orient = which_orientation(event.values[0]);
    if (new_orient == ORIENTATION_UNDETERMINED)
      return;

    if (new_orient != mCurOrientation) {
      mCurOrientation = new_orient;
      if (mRotateListener != null) {
        mRotateListener.onRotate(new_orient);
      }
    }
  }

  private int which_orientation(float angle)
  {
    if ((angle < 90) && (angle > 0))
      return ORIENTATION_NORTH;
    if ((angle < 180) && (angle > 90))
      return ORIENTATION_EAST;
    if ((angle < 270) && (angle > 180))
      return ORIENTATION_SOUTH;
    if ((angle < 360) && (angle > 270))
      return ORIENTATION_WEST;
    return ORIENTATION_UNDETERMINED;
  }

  public void setOnRotateListener(OnRotateListener lstnr)
  {
    mRotateListener = lstnr;
  }
}




/* The following code was written by Matthew Wiggins
 * and is released under the APACHE 2.0 license
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.hlidskialf.android.hardware;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.content.Context;
import java.lang.UnsupportedOperationException;

public class ShakeListener implements SensorListener
{
  private static final int FORCE_THRESHOLD = 350;
  private static final int TIME_THRESHOLD = 100;
  private static final int SHAKE_TIMEOUT = 500;
  private static final int SHAKE_DURATION = 1000;
  private static final int SHAKE_COUNT = 3;

  private SensorManager mSensorMgr;
  private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
  private long mLastTime;
  private OnShakeListener mShakeListener;
  private Context mContext;
  private int mShakeCount = 0;
  private long mLastShake;
  private long mLastForce;

  public interface OnShakeListener
  {
    public void onShake();
  }

  public ShakeListener(Context context)
  {
    mContext = context;
    resume();
  }

  public void setOnShakeListener(OnShakeListener listener)
  {
    mShakeListener = listener;
  }

  public void resume() {
    mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
    if (mSensorMgr == null) {
      throw new UnsupportedOperationException("Sensors not supported");
    }
    boolean supported = mSensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
    if (!supported) {
      mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
      throw new UnsupportedOperationException("Accelerometer not supported");
    }
  }

  public void pause() {
    if (mSensorMgr != null) {
      mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
      mSensorMgr = null;
    }
  }

  public void onAccuracyChanged(int sensor, int accuracy) { }

  public void onSensorChanged(int sensor, float[] values)
  {
    if (sensor != SensorManager.SENSOR_ACCELEROMETER) return;
    long now = System.currentTimeMillis();

    if ((now - mLastForce) > SHAKE_TIMEOUT) {
      mShakeCount = 0;
    }

    if ((now - mLastTime) > TIME_THRESHOLD) {
      long diff = now - mLastTime;
      float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
      if (speed > FORCE_THRESHOLD) {
        if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
          mLastShake = now;
          mShakeCount = 0;
          if (mShakeListener != null) {
            mShakeListener.onShake();
          }
        }
        mLastForce = now;
      }
      mLastTime = now;
      mLastX = values[SensorManager.DATA_X];
      mLastY = values[SensorManager.DATA_Y];
      mLastZ = values[SensorManager.DATA_Z];
    }
  }

}

A simple testbed activity

public class ShakeListenerTestActivity extends Activity
{
  private ShakeListener mShaker;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

    mShaker = new ShakeListener(this);
    mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
      public void onShake()
      {
        vibe.vibrate(100);
        new AlertDialog.Builder(Testy.this)
          .setPositiveButton(android.R.string.ok, null)
          .setMessage("Shooken!")
          .show();
      }
    });
  }

  @Override
  public void onResume()
  {
    mShaker.resume();
    super.onResume();
  }
  @Override
  public void onPause()
  {
    mShaker.pause();
    super.onPause();
  }
}






package com.hlidskialf.android.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.preference.DialogPreference;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout;


public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener
{
  private static final String androidns="http://schemas.android.com/apk/res/android";

  private SeekBar mSeekBar;
  private TextView mSplashText,mValueText;
  private Context mContext;

  private String mDialogMessage, mSuffix;
  private int mDefault, mMax, mValue = 0;

  public SeekBarPreference(Context context, AttributeSet attrs) {
    super(context,attrs);
    mContext = context;

    mDialogMessage = attrs.getAttributeValue(androidns,"dialogMessage");
    mSuffix = attrs.getAttributeValue(androidns,"text");
    mDefault = attrs.getAttributeIntValue(androidns,"defaultValue", 0);
    mMax = attrs.getAttributeIntValue(androidns,"max", 100);

  }
  @Override
  protected View onCreateDialogView() {
    LinearLayout.LayoutParams params;
    LinearLayout layout = new LinearLayout(mContext);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(6,6,6,6);

    mSplashText = new TextView(mContext);
    if (mDialogMessage != null)
      mSplashText.setText(mDialogMessage);
    layout.addView(mSplashText);

    mValueText = new TextView(mContext);
    mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
    mValueText.setTextSize(32);
    params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.FILL_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    layout.addView(mValueText, params);

    mSeekBar = new SeekBar(mContext);
    mSeekBar.setOnSeekBarChangeListener(this);
    layout.addView(mSeekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    if (shouldPersist())
      mValue = getPersistedInt(mDefault);

    mSeekBar.setMax(mMax);
    mSeekBar.setProgress(mValue);
    return layout;
  }
  @Override
  protected void onBindDialogView(View v) {
    super.onBindDialogView(v);
    mSeekBar.setMax(mMax);
    mSeekBar.setProgress(mValue);
  }
  @Override
  protected void onSetInitialValue(boolean restore, Object defaultValue)
  {
    super.onSetInitialValue(restore, defaultValue);
    if (restore)
      mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
    else
      mValue = (Integer)defaultValue;
  }

  public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
  {
    String t = String.valueOf(value);
    mValueText.setText(mSuffix == null ? t : t.concat(mSuffix));
    if (shouldPersist())
      persistInt(value);
    callChangeListener(new Integer(value));
  }
  public void onStartTrackingTouch(SeekBar seek) {}
  public void onStopTrackingTouch(SeekBar seek) {}

  public void setMax(int max) { mMax = max; }
  public int getMax() { return mMax; }

  public void setProgress(int progress) {
    mValue = progress;
    if (mSeekBar != null)
      mSeekBar.setProgress(progress);
  }
  public int getProgress() { return mValue; }
}
Here is a sample XML layout

<com.hlidskialf.android.preference.SeekBarPreference android:key="duration"
    android:title="Duration of something"
    android:summary="How long something will last"
    android:dialogMessage="Something duration"
    android:defaultValue="5"
    android:text=" minutes"
    android:max="60"
    />






*/

}
