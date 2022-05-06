package alex.aigrafico;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;


public class FunctionStructure
{
    EditText text;
    CheckBox state;
    Integer color;
    LinearLayout layout;
    FloatingActionButton deleteButton;

    public FunctionStructure(EditText tx,CheckBox st, Integer col,LinearLayout la,FloatingActionButton but)
    {
        text=tx;
        state=st;
        color=col;
        layout=la;
        deleteButton=but;
    }

    public boolean drawable()
    {
        return state.isChecked();
    }

    public int getColor()
    {
        return color;
    }

    public String getText()
    {
        return text.getText().toString();
    }
    public String getName()
    {
        return state.getText().toString().substring(0,1);
    }

    public void HideFunction()
    {
        text.setText("");
        state.setChecked(false);
        layout.setVisibility(View.GONE);
    }
}
