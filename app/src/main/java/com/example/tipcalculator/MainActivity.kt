package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.View
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

	private val TipViewModel : tipViewModel by lazy {
		ViewModelProviders.of(this).get(tipViewModel::class.java)
	}

	//Bill amount
	private lateinit var editTextBillAmount : EditText

	//Percent amount
	private lateinit var textViewPercentDisplay : TextView
	private lateinit var buttonPercentMinus : Button
	private lateinit var buttonPercentPlus : Button

	//Tip
	private lateinit var textViewTipAmount : TextView

	//Total
	private lateinit var textViewTotalAmount : TextView
	var billAmount : Double = 0.0

	private val KEY_TIP = "tip"
	private val KEY_TOTAL = "total"
	private val KEY_PERCENT = "percent"

	private var TIP = 0.0
	private var TOTAL = 0.0
	private var PERCENT = 0


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//retrieve
		TIP = savedInstanceState?.getDouble(KEY_TIP, 0.0) ?:0.0
		TOTAL = savedInstanceState?.getDouble(KEY_TOTAL, 0.0) ?:0.0
		PERCENT = savedInstanceState?.getInt(KEY_PERCENT, 0) ?:0

		//inflate the layout elements
		editTextBillAmount = findViewById(R.id.edit_text_bill_amount)

		textViewPercentDisplay = findViewById(R.id.text_view_percent_display)
		buttonPercentMinus = findViewById(R.id.button_percent_minus)
		buttonPercentPlus = findViewById(R.id.button_percent_plus)

		textViewTipAmount = findViewById(R.id.text_view_tip_amount)

		textViewTotalAmount = findViewById(R.id.text_view_total_amount)


		textViewPercentDisplay.text = TipViewModel.percent.toString()
		textViewTipAmount.text = TipViewModel.tip.toString()
		textViewTotalAmount.text = TipViewModel.total.toString()


		editTextBillAmount.setOnClickListener{
				view: View -> computeTip(2) // default tip percent
		}

		buttonPercentMinus.setOnClickListener{
				view: View -> computeTip(0)   //decrease tip percent
		}

		buttonPercentPlus.setOnClickListener{
				view: View -> computeTip(1) //increase tip percent
		}
	}

	public fun computeTip(action : Int) {

		var billAmount = editTextBillAmount.getText().toString().toDouble()
		var tipPercent = textViewPercentDisplay.getText().toString().toInt()

		if(action == 0) {
			if (tipPercent > 2)
				tipPercent--
		}
		else if(action == 1)
			tipPercent++

		var tip = (billAmount * tipPercent * 0.01 * 100).toInt() / 100.0

		var total = ((billAmount + tip) * 100).toInt() / 100.0

		TipViewModel.percent = tipPercent
		PERCENT = tipPercent

		TipViewModel.tip = tip
		TIP = tip

		TipViewModel.total = total
		TOTAL = total

		textViewTipAmount.setText("$${TipViewModel.tip}")
		textViewTotalAmount.setText("$${TipViewModel.total}")
		textViewPercentDisplay.setText("${TipViewModel.percent}")

		textViewTipAmount.setText("$${TIP}")
		textViewTotalAmount.setText("$${TOTAL}")
		textViewPercentDisplay.setText("${PERCENT}")
	}

	override fun onSaveInstanceState(savedInstanceState: Bundle) {
		super.onSaveInstanceState(savedInstanceState)
		savedInstanceState.putDouble(KEY_TIP, TIP)
		savedInstanceState.putDouble(KEY_TOTAL, TOTAL)
		savedInstanceState.putInt(KEY_PERCENT, PERCENT)
	}
}