package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.SeekBar
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    var light_gyroflex: Boolean = false
    var flashlight: Boolean = false
    var stable: Boolean = true;
    var message: String = ""

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val http: String = "http://10.0.1.1/"
    val limitNegative: Float = -1.5f
    val limitPositive: Float = 1.5f
    val PIN_DIRECTION_LEFT: String = "pin_left"             // 1
    val PIN_DIRECTION_RIGHT: String = "pin_right"           // 2
    val PIN_GYROFLEX: String = "pin_gyro"                   // 3
    val PIN_BUZZER: String = "pin_buzzer"                   // 4
    val PIN_DIRECTION_FRONT_1: String = "pin_front_1"       // 5
    val PIN_DIRECTION_FRONT_2: String = "pin_front_2"       // 6
    val PIN_DIRECTION_FRONT_3: String = "pin_front_3"       // 7
    val PIN_DIRECTION_FRONT_4: String = "pin_front_4"       // 8
    val PIN_DIRECTION_BACK_1: String = "pin_back_1"         // 9
    val PIN_DIRECTION_BACK_2: String = "pin_back_2"         // 10

    fun flasherGyroflex() {
        var animationIDs = intArrayOf(R.anim.blink)//R.anim.fade_in, R.anim.fade_out, R.anim.zoom_in, R.anim.zoom_out, R.anim.blink, R.anim.rotate, R.anim.move, R.anim.slide_up, R.anim.slide_down, R.anim.bounce)
        val animation = AnimationUtils.loadAnimation(this, animationIDs[0])

        if (!light_gyroflex) {
            img_light_gyroflex.startAnimation(animation)
            execHttp(PIN_GYROFLEX + "1")
        } else {
            img_light_gyroflex.clearAnimation()
            execHttp(PIN_GYROFLEX + "0")
        }

        light_gyroflex = light_gyroflex.not()
    }

    //@TODO Implementar vibração
    // act = true >> liga || act = false >> desliga
    fun flasherFlashlight(img: ImageView, act: Boolean) {
        if (flashlight == act && act) {return}

        var animationIDs = intArrayOf(R.anim.blink_2)
        val animation = AnimationUtils.loadAnimation(this, animationIDs[0])

        if (act) {
            img.startAnimation(animation)
        } else {
            img.clearAnimation()
        }

        flashlight = act
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        resetActionDirection()

        btn_gyroflex.setOnClickListener{
            flasherGyroflex()
        }

        switch_buzzer.setOnClickListener{
            if (switch_buzzer.isChecked) {

            } else {

            }

            //@TODO Implementar no receptor
            //execHttp(PIN + "0")
        }


        //@TODO Quando tiver mais opções de velocidade, implementar como exclusivo
        button_back_1.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    execHttp(PIN_DIRECTION_BACK_1 + "1")
                }
                KeyEvent.ACTION_UP -> {
                    execHttp(PIN_DIRECTION_BACK_1 + "0")
                }
            }

            true
        }

        seekBar_accelerator.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            var seekBarProgress: Int = 0

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //toast("Progress: " + seekBarProgress + " / " + seekBar.getMax())

                when (seekBarProgress) {
                    0 -> {
                        //
                        execHttp(PIN_DIRECTION_FRONT_1 + "0")
                    }
                    1 -> {
                        //
                        execHttp(PIN_DIRECTION_FRONT_1 + "1")
                    }
                    2 -> {
                        //
                    }
                    3 -> {
                        //
                    }
                }
            }
        })
    }

    //
    // act = true >> ligar || act = false >> desligar
    fun actionExtras(pin: String, act: Boolean) {

        //@TODO Implementar no receptor
        //execHttp(PIN + "0")
    }

    //
    fun actionMove() {


        //@TODO Implementar no receptor
        //execHttp(PIN + "0")
    }

    fun resetActionDirection() {
        imgLeft.visibility = View.INVISIBLE
        imgRight.visibility = View.INVISIBLE

        flasherFlashlight(img_flashelight_left, false)
        flasherFlashlight(img_flashlight_right, false)
    }

    fun execHttp(data: String) {
        if (toggle_button_master_key.isChecked && !message.equals(data)) {
            message = data
            Fuel.get(http + data).response { request, response, result ->
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

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL

                //SENSOR_DELAY_FASTEST: retorna os dados do sensor o mais rápido possível;
                //SENSOR_DELAY_GAME: utiliza uma taxa adequada para jogos;
                //SENSOR_DELAY_NORMAL: taxa adequada para mudanças na orientação da tela;
                //SENSOR_DELAY_UI: taxa adequada para a interface de usuário.
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var float_ey: Float = event!!.values.get(1)
        //println(float_ey)
        text2.text = float_ey.toString()

        if (float_ey > limitNegative && float_ey < limitPositive) {
            if (!stable) {
                execHttp(PIN_DIRECTION_LEFT + "0")
                execHttp(PIN_DIRECTION_RIGHT + "0")
                resetActionDirection()
                stable = true
            }
        }

        if (float_ey > limitPositive) {
            execHttp(PIN_DIRECTION_RIGHT + "1")
            imgRight.visibility = View.VISIBLE
            flasherFlashlight(img_flashlight_right, true)
            stable = false
        }

        if (float_ey < limitNegative) {
            execHttp(PIN_DIRECTION_LEFT + "1")
            imgLeft.visibility = View.VISIBLE
            flasherFlashlight(img_flashelight_left, true)
            stable = false
        }

/*

        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];

         /*
        Os valores ocilam de -10 a 10.
        Quanto maior o valor de X mais ele ta caindo para a esquerda - Positivo Esqueda
        Quanto menor o valor de X mais ele ta caindo para a direita  - Negativo Direita
        Se o valor de  X for 0 então o celular ta em pé - Nem Direita Nem Esquerda
        Se o valor de Y for 0 então o cel ta "deitado"
         Se o valor de Y for negativo então ta de cabeça pra baixo, então quanto menor y mais ele ta inclinando pra ir pra baixo
        Se o valor de Z for 0 então o dispositivo esta reto na horizontal.
        Quanto maioro o valor de Z Mais ele esta inclinado para frente
        Quanto menor o valor de Z Mais ele esta inclinado para traz.
        */
        textViewX.setText("Posição X: " + x.intValue() + " Float: " + x);
        textViewY.setText("Posição Y: " + y.intValue() + " Float: " + y);
        textViewZ.setText("Posição Z: " + z.intValue() + " Float: " + z);

        if(y < 0) { // O dispositivo esta de cabeça pra baixo
            if(x > 0)
                textViewDetail.setText("Virando para ESQUERDA ficando INVERTIDO");
            if(x < 0)
                textViewDetail.setText("Virando para DIREITA ficando INVERTIDO");
        } else {
            if(x > 0)
                textViewDetail.setText("Virando para ESQUERDA ");
            if(x < 0)
                textViewDetail.setText("Virando para DIREITA ");
        }





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

}
