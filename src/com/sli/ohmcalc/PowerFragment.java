package com.sli.ohmcalc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class PowerFragment extends SherlockFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static class Power extends SherlockFragment {

		private EditText etVoltage, etCurrent, etResistance, etPower;
		private Button bCalculate, bClear;
		private Context context;

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater
					.inflate(R.layout.activity_power, container, false);

			context = getSherlockActivity();

			// Setup logic here!
			etVoltage = (EditText) v.findViewById(R.id.etVoltage);
			etCurrent = (EditText) v.findViewById(R.id.etCurrent);
			etResistance = (EditText) v.findViewById(R.id.etResistance);
			etPower = (EditText) v.findViewById(R.id.etPower);
			bCalculate = (Button) v.findViewById(R.id.bCalculate);
			bClear = (Button) v.findViewById(R.id.bClearRes);

			bCalculate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					float volts = 0f, current = 0f, resistance = 0f, power = 0f;
					int fields = 0;
					String vStr, cStr, rStr, pStr;

					vStr = etVoltage.getText().toString();
					cStr = etCurrent.getText().toString();
					rStr = etResistance.getText().toString();
					pStr = etPower.getText().toString();

					if (!vStr.matches("")) {
						volts = Float.parseFloat(vStr);
						fields++;
					}

					if (!cStr.matches("")) {
						current = Float.parseFloat(cStr);
						fields++;
					}

					if (!rStr.matches("")) {
						resistance = Float.parseFloat(rStr);
						fields++;
					}

					if (!pStr.matches("")) {
						power = Float.parseFloat(pStr);
						fields++;
					}

					if (fields == 2) {
						// Argument order: volts, current, resistance, power
						if (!vStr.matches("") && !cStr.matches("")) {
							// Calculate using volts and current
							Log.d("OhmCalc",
									"Calculating power and resistance.");
							power = CalcPower(volts, current, 0);
							resistance = CalcResistance(volts, current, power);
						} else if (!vStr.matches("") && !rStr.matches("")) {
							// Calculate using volts and resistance
							Log.d("OhmCalc", "Calculating current and power.");
							current = CalcCurrent(volts, resistance, 0);
							power = CalcPower(volts, current, resistance);
						} else if (!vStr.matches("") && !pStr.matches("")) {
							// Calculate using volts and power
							Log.d("OhmCalc",
									"Calculating resistance and current.");
							resistance = CalcResistance(volts, 0, power);
							current = CalcCurrent(volts, resistance, power);
						} else if (!cStr.matches("") && !rStr.matches("")) {
							// Calculate using current and resistance
							Log.d("OhmCalc", "Calculating volts and power.");
							volts = CalcVolts(current, resistance, 0);
							power = CalcPower(volts, current, resistance);
						} else if (!cStr.matches("") && !pStr.matches("")) {
							// Calcute using current and power
							Log.d("OhmCalc",
									"Calculating volts and resistance.");
							volts = CalcVolts(current, 0, power);
							resistance = CalcResistance(volts, current, power);
						} else if (!rStr.matches("") && !pStr.matches("")) {
							// Calculate using resistance and power
							Log.d("OhmCalc", "Calculating volts and current.");
							volts = CalcVolts(0, resistance, power);
							current = CalcCurrent(volts, resistance, power);
						} else {
							Toast toast = Toast.makeText(context,
									"Something is seriously wrong here.",
									Toast.LENGTH_LONG);
							toast.show();
						}

						etVoltage.setText(Float.toString(volts));
						etCurrent.setText(Float.toString(current));
						etResistance.setText(Float.toString(resistance));
						etPower.setText(Float.toString(power));
					} else {
						// Cannot continue!
						Toast toast = Toast.makeText(context,
								"You must enter exactly two values.",
								Toast.LENGTH_LONG);
						toast.show();
					}
				}

			});

			bClear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etVoltage.setText("");
					etCurrent.setText("");
					etResistance.setText("");
					etPower.setText("");
				}

			});

			return v;
		}

		public static float CalcVolts(float I, float R, float P) {
			float E;

			if (I == 0) {
				E = (float) Math.sqrt(P * R);
			} else if (R == 0.0) {
				E = P / I;
			} else {
				E = I * R;
			}

			return E;
		}

		public static float CalcCurrent(float E, float R, float P) {
			float I;

			if (P == 0) {
				I = E / R;
			} else if (R == 0.0) {
				I = P / E;
			} else {
				I = (float) Math.sqrt(P / R);
			}

			return I;
		}

		public static float CalcResistance(float E, float I, float P) {
			float R;

			if (P == 0.0) {
				R = E / I;
			} else if (I == 0) {
				R = (float) Math.pow(E, 2) / P;
			} else {
				R = P / (float) Math.pow(I, 2);
			}

			return R;
		}

		public static float CalcPower(float E, float I, float R) {
			float P;

			if (I == 0) {
				P = (float) Math.pow(E, 2) / R;
			} else if (E == 0) {
				P = (float) Math.pow(I, 2) * R;
			} else {
				P = E * I;
			}

			return P;
		}
	}
}
