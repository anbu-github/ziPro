package com.purplefront.jiro_dev;

import android.widget.CheckBox;

import com.purplefront.jiro_dev.model.AddressSelection;
import com.purplefront.jiro_dev.model.AttributeModel;

import java.util.ArrayList;

/**
 * Created by pf-05 on 7/31/2016.
 */
public class StaticVariable {

    public static String business_id = "";
    public static String user_id = "";
    public static String email = "";
    public static String role_id = "";
    public static String name = "";

    public static String object_id = "";


    public static String objectid="";

    public static String cusobj_id="";

    public static String contact = "";
    public static String groupid = "";

    public static String customer_segment_id = "";

    public static String customer_segment_cust_id = "";

    public static String grpdisplay_backcustbtn = "";

    public static Integer cbpos1 = -1;
    public static String companyContact = "0";

    public static Integer cbpos=-1;

    public static String customerid = "0";

    public static String cus_seg_objectid = "";


    public static String customerContactName = "0";

    public static String customerContactid = "0";


    public static String dispcustomername = "";

    public static String dispcustomername1 = "";


    public static Boolean isSelected1 = false;
    public static String address_id = "";
    public static String address_id1 = "";

    public static String titleid = "0";

    public static String titlename = "";

    public static String countryid = "0";
    public static String countryname = "";

    public static String stateid = "0";
    public static String statename = "";


    public static String stateContact = "0";

    public static String catalog_id = "0";

    public static String currency_id = "0";

    public static String tax_id = "0";
    public static String taxName = "";

    public static String assignuser_id = "0";

    public static String userass_id = "0";

    public static String conta_id = "0";

    public static String custo_id = "0";


    public static String shipId = "";
    public static String shipAdd1 = "";
    public static String shipAdd2 = "";
    public static String shipAdd3 = "";
    public static String shipCity = "";
    public static String shipState = "";
    public static String shipPincode = "";


    public static String billId = "";
    public static String billAdd1 = "";
    public static String billAdd2 = "";
    public static String billAdd3 = "";
    public static String billCity = "";
    public static String billState = "";
    public static String billPincode = "";

    public static Integer billposId = 0;
    public static Integer shipposId = 0;


    public static String dispContactName = "";

    public static String dispContactName1 = "";

    public static String dispUserName = "";

    public static String dispCataName = "";

    public static String custom_id="";

    public static String currencyName = "";

    public static String umo_priceid = "";

    public static Integer total_amt = 0;
    public static Float net_amount = 0.00f;
    public static String pickupDate;
    public static String pickedDateTIme="";

    public static Integer cbPosition;

    public  static   ArrayList<String> addressIdList=new ArrayList<>();

    public static Boolean isSelected=false;

    public static String customer_visit_id = "";

    public static String catalognamedisp="";

    public static int lastCheckedPos = 0;

    public static CheckBox lastChecked = null;

    public static Integer per_id=0;

    public static String act_id="";


   public static ArrayList<String> idList = new ArrayList<>();
   public static ArrayList<String> actionidList = new ArrayList<>();
   public static ArrayList<String> permissionidList = new ArrayList<>();


    public static ArrayList<String> qtylist = new ArrayList<>();
    public static ArrayList<String> umo_name_pricelist = new ArrayList<>();
    public static ArrayList<String> pro_total_amtlist = new ArrayList<>();
    public static ArrayList<Integer> uom_pos_list = new ArrayList<>();
    public static ArrayList<String> uom_price_list = new ArrayList<>();
    public static ArrayList<AttributeModel> customattrlist = new ArrayList<>();


    public static AddressSelection billaddselection = new AddressSelection();
    public static AddressSelection shipaddselection = new AddressSelection();




}
