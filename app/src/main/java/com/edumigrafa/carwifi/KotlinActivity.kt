package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    var luz_giroflex: Boolean = false
    var luz_pisca_esquerda: Boolean = false
    var luz_pisca_direita: Boolean = false

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    fun piscaGiroflex() {
        var animationIDs = intArrayOf(R.anim.blink)//R.anim.fade_in, R.anim.fade_out, R.anim.zoom_in, R.anim.zoom_out, R.anim.blink, R.anim.rotate, R.anim.move, R.anim.slide_up, R.anim.slide_down, R.anim.bounce)
        val animation = AnimationUtils.loadAnimation(this, animationIDs[0])

        if (!luz_giroflex) {
            img_luz_giroflex.startAnimation(animation)
        } else {
            img_luz_giroflex.clearAnimation()
        }

        luz_giroflex = luz_giroflex.not()
    }

    // lado = true >> esquerda || lado = false >> direita
    fun piscaLanterna(img: ImageView, lado: Boolean): Boolean {
        var animationIDs = intArrayOf(R.anim.blink_2)
        val animation = AnimationUtils.loadAnimation(this, animationIDs[0])
        var flag: Boolean = if (lado) { luz_pisca_esquerda } else { luz_pisca_direita }

        if (!flag) {
            img.startAnimation(animation)
        } else {
            img.clearAnimation()
        }

        //if(lado) {
        //    luz_pisca_esquerda != luz_pisca_esquerda
        //} else {
        //    luz_pisca_direita != luz_pisca_direita
        //}

        return flag.not()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        btn_giroflex.setOnClickListener{
            piscaGiroflex()
        }

        btn_farol_esquerdo.setOnClickListener{
            //var animationIDs = intArrayOf(R.anim.blink)//R.anim.fade_in, R.anim.fade_out, R.anim.zoom_in, R.anim.zoom_out, R.anim.blink, R.anim.rotate, R.anim.move, R.anim.slide_up, R.anim.slide_down, R.anim.bounce)
            //val animation = AnimationUtils.loadAnimation(this, animationIDs[0])

            //if (!luz_farol) {
            //    img_farol_esquerda_2.visibility = View.VISIBLE
            //    img_farol_esquerda_2.startAnimation(animation)
            //} else {
            //    img_farol_esquerda_2.clearAnimation()
            //    img_farol_esquerda_2.visibility = View.INVISIBLE
            //}

            //luz_farol = luz_farol.not()

            luz_pisca_esquerda = piscaLanterna(img_lanterna_esquerda, true)
        }

        btn_farol_direito.setOnClickListener{
            luz_pisca_direita = piscaLanterna(img_lanterna_direita, false)
        }


        /*
        imgCar.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    //action("L1", "1")
                    var str = motionEvent.x.toString()
                    str += motionEvent.y.toString()
                    text2.setText(str)
                }
                KeyEvent.ACTION_UP -> {
                    //action("L1", "0")
                    text2.setText("")
                }
            }

            true
        }


        imgLeft.visibility = View.INVISIBLE
        imgRight.visibility = View.INVISIBLE
        imgFront.visibility = View.INVISIBLE
        imgBack.visibility = View.INVISIBLE

        led1.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    action("L1", "1")
                }
                KeyEvent.ACTION_UP -> {
                    action("L1", "0")
                }
            }

            true
        }

        led2.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    action("L2", "1")
                }
                KeyEvent.ACTION_UP -> {
                    action("L2", "0")
                }
            }

            true
        }

        switch1.setOnClickListener {

            var led: String = if (switch1.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L1" + led).response { request, response, result ->
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

        switch2.setOnClickListener {

            var led: String = if (switch2.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L2" + led).response { request, response, result ->
                //println(request)
                //println(response)
                //val (bytes, error) = result
                //if (bytes != null) {
                //    println(bytes)
                //}

                //text.text("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }

        switch3.setOnClickListener {

            var led: String = if (switch3.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L3" + led).response { request, response, result ->
                //println(request)
                //println(response)
                //val (bytes, error) = result
                //if (bytes != null) {
                //    println(bytes)
                //}

                //text.text("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }
*/
    }

    fun action(led: String, act: String) {

        Fuel.get("http://10.0.1.1/" + led + act).response { request, response, result ->
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

    var stable: Boolean = true;
    override fun onSensorChanged(event: SensorEvent?) {

        //text.text = event!!.values.zip("XYZ".toList()).fold("") { acc, pair ->
        //    "$acc${pair.second}: ${pair.first}\n"
        //}

        //text2.text = event!!.values.zip("Y".toList()).fold("Y") { acc, pair ->
        //    "$acc${pair.second}: ${pair.first}\n"
        //}

        //text.text = event!!.values.zip("Y".toList()).fold("") { acc, pair ->
        //    "$acc: ${pair.first}\n"
        //}

        //var str_ey: String = event!!.values.zip("X".toList()).fold("") { acc, pair ->
        //    "$acc: ${pair.first}\n"
        //}

        //println(str_ey)

        var float_ey: Float = event!!.values.get(1)
        //println(float_ey)
        //text2.text = float_ey.toString()


        if (float_ey > -1.5 && float_ey < 1.5) {
            if (!stable) {
                action("L1", "0")
                action("L2", "0")
                stable = true
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.INVISIBLE
                imgFront.visibility = View.INVISIBLE
                imgBack.visibility = View.INVISIBLE
            }
        }

        if (float_ey > 1.5) {
            action("L1", "1")
            stable = false
            imgRight.visibility = View.VISIBLE
        }

        if (float_ey < -1.5) {
            action("L2", "1")
            stable = false
            imgLeft.visibility = View.VISIBLE
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
