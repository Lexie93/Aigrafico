package alex.aigrafico;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fathzer.soft.javaluator.*;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LineChart chart = (LineChart) findViewById(R.id.chart);

        chart.getLegend().setEnabled(true);
        chart.setMaxVisibleValueCount(10);
        chart.setNoDataText("");
        chart.setDescription("");
        DoubleEvaluator evaluator=new DoubleEvaluator();

        FloatingActionButton b1=(FloatingActionButton) findViewById(R.id.floatingActionButtonDraw);

        EditText function_edit[]=new EditText[4];
        function_edit[0]= (EditText) findViewById(R.id.editText);
        function_edit[1]= (EditText) findViewById(R.id.editText2);
        function_edit[2]= (EditText) findViewById(R.id.editText3);
        function_edit[3]= (EditText) findViewById(R.id.editText4);

        EditText startvalue_edit=(EditText) findViewById(R.id.startX_edit);
        EditText endvalue_edit=(EditText) findViewById(R.id.endX_edit);

        CheckBox funct_chk[]=new CheckBox[4];
         funct_chk[0]= (CheckBox) findViewById(R.id.checkBox);
         funct_chk[1]= (CheckBox) findViewById(R.id.checkBox2);
         funct_chk[2]= (CheckBox) findViewById(R.id.checkBox3);
         funct_chk[3]= (CheckBox) findViewById(R.id.checkBox4);

        int colors[]=new int[4];
        colors[0]=ContextCompat.getColor(this.getApplicationContext(),R.color.celeste);
        colors[1]=ContextCompat.getColor(this.getApplicationContext(),R.color.Arancione);
        colors[2]=ContextCompat.getColor(this.getApplicationContext(),R.color.Verde);
        colors[3]=ContextCompat.getColor(this.getApplicationContext(),R.color.Viola);
        chart.getLegend().setCustom(colors, new String[]{funct_chk[0].getText().toString(),funct_chk[1].getText().toString(), funct_chk[2].getText().toString(), funct_chk[3].getText().toString()});
        FloatingActionButton ButtonAdd=(FloatingActionButton) findViewById(R.id.floatingActionButtonAdd);

        FloatingActionButton ButtonDelete[]=new FloatingActionButton[4];

         ButtonDelete[0]=(FloatingActionButton) findViewById(R.id.floatingActionButtonDelete);
         ButtonDelete[1]=(FloatingActionButton) findViewById(R.id.floatingActionButtonDelete2);
         ButtonDelete[2]=(FloatingActionButton) findViewById(R.id.floatingActionButtonDelete3);
         ButtonDelete[3]=(FloatingActionButton) findViewById(R.id.floatingActionButtonDelete4);

        LinearLayout layout[]=new LinearLayout[4];

        layout[0]=(LinearLayout) findViewById(R.id.Layout1);
        layout[1]=(LinearLayout) findViewById(R.id.Layout2);
        layout[2]=(LinearLayout) findViewById(R.id.Layout3);
        layout[3]=(LinearLayout) findViewById(R.id.Layout4);

        FunctionStructure functions[]=new FunctionStructure[4];

        for(int i=0;i<4;i++)
        {
            functions[i]=new FunctionStructure(function_edit[i],funct_chk[i],colors[i],layout[i],ButtonDelete[i]);
        }

        ButtonListener chartListener= new ButtonListener(chart,evaluator,functions,getApplicationContext(),startvalue_edit,endvalue_edit);
        b1.setOnClickListener(chartListener);

        FunctionVisibilityHandler functionButtonListener = new FunctionVisibilityHandler(functions,ButtonAdd,chartListener);

        ButtonAdd.setOnClickListener(functionButtonListener);

        for(int i=0;i<4;i++)
        {
            ButtonDelete[i].setOnClickListener(functionButtonListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),getString(R.string.credits_msg), Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
