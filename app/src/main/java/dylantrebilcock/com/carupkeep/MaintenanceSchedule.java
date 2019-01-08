package dylantrebilcock.com.carupkeep;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MaintenanceSchedule {

    private static Spinner spinMake;
    private static EditText mCurrentDist;
    private static TextView maintenance;

    private static int miles = Integer.parseInt(mCurrentDist.toString());

    public static String makeMaintenance(Vehicle vehicle) { // Sends vehicle to proper method that is holding Manufacturer specific maintenance schedules

        String make = spinMake.getSelectedItem().toString();
    switch (make) {
        case "Acura":
            acuraMaintenance();
            break;

        case "Audi":
            audiMaintenance();
            break;

        case "Buick":
        case "Cadillac:":
            buickCadillacMaintenance();
            break;

        case "BMW":
            bmwMaintenance();
            break;

        case "Oldsmobile":
        case "Pontiac":
        case "Saturn":
            oldsmobilePontiacSaturnMaintenance();
            break;

        case "Tesla":
            teslaMaintenance();
            break;
        }
        return make;
    }

    //  Below are all the Make-specific maintenance schedules for the vehicles when they hit a certain mileage

    private static void acuraMaintenance() {

        if (miles % 7500 == 0) {
            maintenance.setText(R.string.MaintenanceChangeOil);
        } else if ((miles % 15000 == 0)) {
            maintenance.setText("Change Differential fluid");
        } else if (miles % 40000 == 0) {
            maintenance.setText("Replace Brake pads/shoes and Flush Brake fluid");
        } else if (miles % 60000 == 0) {
            maintenance.setText("Flush Coolant and Transmission fluids as well as Replace the Water Pump");
        } else if (miles % 80000 == 0) {
            maintenance.setText("Change Spark Plugs and Timing Belt as well as Flush the Power Steering Fluid");
        } else if (miles == 100000) {
            maintenance.setText(R.string.MileageCongrats);
        } else if (miles % 160000 == 0) {
            maintenance.setText("Inspect Idle speed");
        }

    }

    private static void audiMaintenance() {

        if (miles % 7500 == 0) {
            maintenance.setText(R.string.MaintenanceChangeOil);
        } else if (miles % 20000 == 0) {
            maintenance.setText("Change Air Filter");
        } else if (miles % 40000 == 0) {
            maintenance.setText("Replace Brake pads/shoes and Flush Brake fluid");
        } else if (miles % 47500 == 0) {
            maintenance.setText("Replace Spark Plugs");
        } else if (miles % 55000 == 0) {
            maintenance.setText("Replace Fuel Filter");
        } else if (miles % 60000 == 0) {
            maintenance.setText("Flush Coolant and Transmission fluids");
        } else if (miles == 80000) {
            maintenance.setText("Replace Power Steering FLuid");
        } else if (miles == 100000) {
            maintenance.setText(R.string.MileageCongrats);
        } else if (miles % 110000 == 0) {
            maintenance.setText("Change Timing Belt");
        } else if (miles % 125000 == 0) {
            maintenance.setText("Replace Diesel Particulate Filter");
        }
    }

    private static void bmwMaintenance() {

        if(miles % 7500 == 0){
            maintenance.setText(R.string.MaintenanceChangeOil);
        } else if (miles % 30000 == 0) {
            maintenance.setText("Replace Air and Fuel Filters");
        } else if (miles % 40000 == 0) {
            maintenance.setText("Change Brake Pads/Shoes and Replace Brake Fluid");
        } else if (miles % 52500 == 0) {
            maintenance.setText("Replace Spark Plugs");
        } else if (miles % 60000 == 0) {
            maintenance.setText("Change Coolant");
        } else if (miles % 80000 == 0) {
            maintenance.setText("Replace Timing Belt as well as Change Power Steering Fluid");
        } else if (miles % 100000 == 0) {
            maintenance.setText("Change Transmission Fluid");
        } else if (miles == 100000) {
            maintenance.setText(R.string.MileageCongrats);
        } else if (miles % 120000 == 0) {
            maintenance.setText("Replace Oxygen (o2) Sensor");
        }
    }

    private static void buickCadillacMaintenance() {

        if(miles % 7500 == 0){
            maintenance.setText(R.string.MaintenanceChangeOil);
        } else if (miles % 22500 == 0) {
          maintenance.setText("Replace passenger compartment air filter");
        } else if (miles % 40000 == 0) {
            maintenance.setText("Change Brake Pads/Shoes");
        } else if (miles % 45000 == 0) {
            maintenance.setText("Change Transmission, Transfer Case, and Brake Fluids. As well as Replace Engine Air Filter. Inspect Evaporative Control System");
        } else if (miles % 60000 == 0) {
            maintenance.setText("Change Coolant");
        } else if (miles % 80000 == 0) {
            maintenance.setText("Change Power Steering Fluid");
        } else if (miles % 97500 == 0) {
            maintenance.setText("Replace Spark Plugs");
        } else if (miles  == 100000) {
            maintenance.setText(R.string.MileageCongrats);
        } else if (miles % 150000 == 0) {
            maintenance.setText("Replace Timing Belt and Change Engine Cooling System");
        }
    }

    private static void oldsmobilePontiacSaturnMaintenance() {

       if(miles % 7500 == 0) {
           maintenance.setText(R.string.MaintenanceChangeOil);
       } else if (miles % 15000 == 0) {
           maintenance.setText("Replace Engine Air Filter");
       } else if (miles % 30000 == 0) {
           maintenance.setText("Replace Fuel Filter");
       } else if (miles % 40000 == 0) {
           maintenance.setText("Change Brake Pads/Shoes and Replace Brake Fluid");
       } else if (miles % 50000 == 0) {
           maintenance.setText("Change Transaxle Fluid");
       } else if (miles % 60000 == 0) {
           maintenance.setText("Flush Coolant and Transmission fluids");
       } else if (miles % 80000 == 0) {
           maintenance.setText("Replace Timing Belt and Change Power Steering Fluid");
       } else if (miles  == 100000) {
           maintenance.setText(R.string.MileageCongrats);
       } else if (miles % 100000 == 0) {
           maintenance.setText("Replace Spark Plugs and Wires");
       } else if (miles % 150000 == 0) {
           maintenance.setText("Flush Cooling System");
       }
    }


    private static void teslaMaintenance() {
        if (miles % 12500 == 0){
            maintenance.setText(R.string.MaintenanceTesla1);
        } else if (miles % 25000 == 0) {
            maintenance.setText(R.string.MaintenanceTesla2);
        } else if (miles % 100000 == 0) {
            maintenance.setText(R.string.MaintenanceTesla3);
        } else if (miles  == 100000) {
            maintenance.setText(R.string.MileageCongrats);
        }
    }
}
