package alex.aigrafico;

import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by carlo on 02/09/2016.
 */

public class FunctionVisibilityHandler implements View.OnClickListener
{

    FunctionStructure functions[];
    FloatingActionButton addButton;
    int numberOfVisibleFunction;
    ButtonListener  chartHandler;

    public FunctionVisibilityHandler(FunctionStructure fun[], FloatingActionButton but, ButtonListener ch)
    {
        functions=new FunctionStructure[fun.length];
        for(int i=0;i<fun.length;i++)
            functions[i]= fun[i];
        numberOfVisibleFunction=1;
        addButton=but;
        chartHandler=ch;
    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.floatingActionButtonAdd) //Add function visibility button pressed
        {
            AddFunctionVisibility();
            if(numberOfVisibleFunction==4)
                v.setVisibility(View.GONE);
        }
        else                        //Remove function visibility button pressed
        {
            functions[findFunction(v)].HideFunction();
            numberOfVisibleFunction--;
            handleCloseButtonVisibility();
            addButton.setVisibility(View.VISIBLE);
            resetChart();
        }
    }

    private int findFunction(View v)
    {
        for(int i=0;i<functions.length;i++)
        if(v.getId()==functions[i].deleteButton.getId())
            return i;
        return -1;
    }

    private void handleCloseButtonVisibility()
    {
        for(int i=0;i<functions.length;i++)
            if((numberOfVisibleFunction==1) && functions[i].layout.getVisibility()==View.VISIBLE)
                functions[i].deleteButton.setVisibility(View.GONE);
    }

    private void AddFunctionVisibility()
    {
        boolean b=true;
        for(int i=0;i<functions.length;i++)
        {
            if(b&& functions[i].layout.getVisibility()==View.GONE)
            {
                functions[i].layout.setVisibility(View.VISIBLE);
                b=false;
            }
            functions[i].deleteButton.setVisibility(View.VISIBLE);
        }
        numberOfVisibleFunction++;
    }

    private void resetChart()
    {
        chartHandler.chart.clear();
        chartHandler.drawFunctions();
        chartHandler.chart.invalidate();
    }
}
