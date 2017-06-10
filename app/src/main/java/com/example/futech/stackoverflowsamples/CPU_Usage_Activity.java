package com.example.futech.stackoverflowsamples;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CPU_Usage_Activity extends AppCompatActivity {


    private static final String TAG = CPU_Usage_Activity.class.getSimpleName();
    private TextView textView_cpuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_usage);
        textView_cpuInfo = (TextView) findViewById(R.id.cpu_info);
        getCpuInfo();
    }

    private void getCpuInfo() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("abi: ").append(Build.CPU_ABI).append("\n");
            if (new File("/proc/stat").exists()) {
                try {
                    BufferedReader br = new BufferedReader(
                            new FileReader(new File("/proc/stat")));
                    String aLine;
                    while ((aLine = br.readLine()) != null) {
                        sb.append(aLine + "\n");
                    }
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, sb.toString());
            textView_cpuInfo.setText(sb.toString());
            String[] lines = sb.toString().split("\n");
            String cpuLine = "";
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("cpu "))
                    cpuLine = lines[i];
            }

            //now that we have obtained individual values, we separate them out
            String[] values = cpuLine.split(" ");
            String idleValue = "";
            int totalCpuTime = 0;
            for (int x = 1; x < values.length; x++) {
                Log.e("value[" + x + "]", values[x]);
                if (!values[x].isEmpty()) {
                    int currentCpuTime = Integer.parseInt(values[x].trim());
                    totalCpuTime += currentCpuTime;
                    if (x == 5)
                        idleValue = values[x];
                }

            }

            // sum up all the columns in the 1st line "cpu" : ( user + nice + system + idle + iowait + irq + softirq )
            // this will yield 100% of CPU time
            Log.e("totalCpuTime", totalCpuTime + "");
            //calculate the average percentage of total 'idle' out of 100% of CPU time :
            // ( user + nice + system + idle + iowait + irq + softirq ) = 100%
            // ( idle ) = X %
            int idleTime = Integer.parseInt(idleValue.trim());
            Log.e("idleTime", idleTime + "");
            // hence
            // average idle percentage X % = ( idle * 100 ) / ( user + nice + system + idle + iowait + irq + softirq )
            float avgIdlePercetage = (idleTime * 100) / (totalCpuTime);
            Log.e("Average Idle Percentage", avgIdlePercetage + " %");
            Log.e("CPU Usage", String.valueOf(100 - avgIdlePercetage) + " %");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
