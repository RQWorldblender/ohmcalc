package com.sli.ohmcalc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ResistanceFragment extends SherlockFragmentActivity {

	private static EditText etResistance1, etResistance2, etResistance3,
			etResistance4;
	private static Button bCalculate;
	private static TextView tvOutput;

	@Override
	public void onCreate(Bundle savedInstance) {
		Log.d("OhmCalc", "Resistance activity starting.");
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_resistance);
		
		etResistance1 = (EditText) findViewById(R.id.etResistance1);
		etResistance2 = (EditText) findViewById(R.id.etResistance2);
		etResistance3 = (EditText) findViewById(R.id.etResistance3);
		etResistance4 = (EditText) findViewById(R.id.etResistance4);
		bCalculate = (Button) findViewById(R.id.bCalculate);
		tvOutput = (TextView) findViewById(R.id.tvOutput);
		
		bCalculate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Calculate total resistance
				float coil1 = 0f, coil2 = 0f, coil3 = 0f, coil4 = 0f, coilTotal = 0f, totalRes;
				String coil;

				coil = etResistance1.getText().toString();
				if (!coil.matches("")) {
					coil1 = Float.parseFloat(coil);
					coilTotal += 1 / coil1;
				}

				coil = etResistance2.getText().toString();
				if (!coil.matches("")) {
					coil2 = Float.parseFloat(coil);
					coilTotal += 1 / coil2;
				}

				coil = etResistance3.getText().toString();
				if (!coil.matches("")) {
					coil3 = Float.parseFloat(coil);
					coilTotal += 1 / coil3;
				}

				coil = etResistance4.getText().toString();
				if (!coil.matches("")) {
					coil4 = Float.parseFloat(coil);
					coilTotal += 1 / coil4;
				}

				totalRes = 1 / coilTotal;
				tvOutput.setText(Float.toString(totalRes)
						+ getString(R.string.ohm_symbol));
			}

		});
		
	}

}
