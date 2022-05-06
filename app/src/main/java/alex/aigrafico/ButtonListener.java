package alex.aigrafico;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class ButtonListener implements View.OnClickListener {

    LineChart chart;
    DoubleEvaluator evaluator;
    FunctionStructure[] functions;
    EditText startValue,endValue;
    final int numberOfValues=200;
    final int transparent;
    Context context;

    public ButtonListener(LineChart ch, DoubleEvaluator ev, FunctionStructure[] fu,Context co,EditText startX,EditText endX)
    {
        chart=ch;
        evaluator=ev;
        functions=new FunctionStructure[fu.length];
        for(int i=0;i<fu.length;i++)
        functions[i]=fu[i];
        context=co;
        transparent=ContextCompat.getColor(context,R.color.trasparente);
        startValue=startX;
        endValue=endX;
    }

    @Override
    public void onClick(View v)
    {
        chart.clear();
        drawFunctions();
        chart.invalidate();
    }

    public void drawFunctions()
    {
        LineData lineData = new LineData();
        Integer xend=getEndvalue();
        Integer xstart=getStartvalue();
        if(xend==null|| xstart==null || xstart>xend)
            printError(context.getString(R.string.error_msg_interval));
        else {
            for (int i = 0; i < functions.length; i++)
                if (functions[i].drawable()) {
                    {
                        List<Entry> entries = functionValues(xstart, xend, numberOfValues, functions[i].getText());
                        LineDataSet dataSet ;
                        if (entries == null || entries.size() == 0)
                        {
                            printError(context.getString(R.string.error_msg_function));
                            return;
                        } else
                        {

                            dataSet =hideAsymptoticValue(entries, i,xend-xstart);
                        }

                        dataSet.setDrawCircles(false);
                        lineData.addDataSet(dataSet);
                    }
                    chart.setData(lineData);
                }
            chart.getXAxis().setAxisMaxValue(xend);
            chart.getXAxis().setAxisMinValue(xstart);

        }
    }

    private  void printError(String msg)
    {
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
    private Integer getStartvalue()
    {
        try {
       //if(startValue.getText().toString()!="")
            return Integer.parseInt(startValue.getText().toString());
        } catch (NumberFormatException e) {
            return null;
        }

    }

    private Integer getEndvalue()
    {
        try {
            //if(endValue.getText().toString()!="")
            return Integer.parseInt(endValue.getText().toString());
        }
        catch (NumberFormatException e) {
            return null;
        }
    }


    private List<Entry> functionValues(double start,double finish,int numberOfValues, String function)
    {
        double x=start;
        double increment=(finish-start)/numberOfValues;
        List<Entry> entries = new ArrayList<Entry>();
        for(int i=0;i<numberOfValues;i++)
        {
            try {
                if (!Double.isInfinite(function(x, function)))
                    entries.add(new Entry((float) x, (float) function(x, function)));
                }
            catch (IllegalArgumentException name) {
            //    return null;
            }
            x=x+increment;
        }
        return entries;
    }
    private double function(Double n,String function)
    {
         function=function.toLowerCase();
        String expression=function.replace("x","("+String.valueOf(n)+")");
        double result = evaluator.evaluate(expression);
        return result;
    }

    private LineDataSet hideAsymptoticValue(List<Entry> values,int indexOfFunction,double interval)
    {
        Float media=0f;
        int colors[]= new int[values.size()-1];
        LineDataSet dataSet;
        int numberOfStandardValue=values.size();
        for(int i=0;i<values.size();i++)
        {
            if(Math.abs(values.get(i).getY())< interval*interval*10)
            {
                media += Math.abs(values.get(i).getY());
            }
            else
                numberOfStandardValue--;
        }
        media=media/numberOfStandardValue;

        colors[0]=functions[indexOfFunction].color;
        for (int i=1;i<colors.length;i++)
        {
            if(  (Math.abs(values.get(i).getY())<=(media*10)  && Math.abs(values.get(i+1).getY())<=(media*10))  )
            {
                colors[i] =functions[indexOfFunction].color;
            }
            else
            {
                if(Math.abs(values.get(i).getY())>(media*10))
                {
                    Entry entry=new Entry(values.get(i).getX(),media);
                 values.set(i,entry);
                }

                colors[i] = transparent;
            }
        }

        if(Math.abs(values.get(colors.length).getY())>(media*10))
        {
            Entry entry=new Entry(values.get(colors.length).getX(),media);
            values.set(colors.length,entry);
        }
        dataSet=new LineDataSet(values, functions[indexOfFunction].getName());
        dataSet.setColors(colors);
        return dataSet;
    }




}
