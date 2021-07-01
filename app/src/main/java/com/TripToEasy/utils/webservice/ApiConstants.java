package com.TripToEasy.utils.webservice;

public class ApiConstants
{

/*  public static String BASE_URL = "http://192.168.0.51/hoyyo/mobile/index.php/";
  public String URL = "http://192.168.0.51/";
  public static String IMAGE_BASE = "http://192.168.0.51/hoyyo/";*/

public static String BASE_URL = "https://www.TripToEasy.com/mobile_webservices/mobile/index.php/";
  public String URL = "https://TripToEasy.com/";
  public static String IMAGE_BASE = "https://TripToEasy.com/mobile_webservices";
    public String URL_COUNTRY_LIST = BASE_URL+"flight/country_list";
    public String HOME_PAGE_API = BASE_URL+"general/mobile_top_destination_list";
    public String USER_LOGIN = BASE_URL+"auth/mobile_login";
    public String USER_SIGN_UP = BASE_URL+"auth/register_on_light_box_mobile";
  public String HOME_UPDATE_CHECK_API = BASE_URL + "utilities/get_app_version";
  public String FACEBOOK_LOGIN = BASE_URL+"user/mob_login_facebook";
    public String GOOLE_LOGIN = BASE_URL+"user/mob_login_google";
    public String CHANGE_PASSWORD=BASE_URL+"general/mobile_change_password";
    public String FORGOT_PASSWORD_OTP=BASE_URL+"general/mobile_forgotpassword";
    public String FORGOT_PASSWORD_CONFIRM=BASE_URL+"general/mobile_forgotpassword_check";
    public String UPDATE_PROFILE = BASE_URL+"user/profile";
    public String CONTACT_US = BASE_URL+"general/contact_us";
    public String ABOUNT_US = BASE_URL+"general/about_us";
    public String PRIVACY_POLICY = BASE_URL+"general/cms_mobile/privacy";
    public String TERMS_AND_CONDITION = BASE_URL+"general/cms_mobile/terms";
    public String CURRENCY_CONVERTER = BASE_URL+"general/currency_converter";
    public static  String URL_PROMOCODE_LIST = BASE_URL+"general/all_promo";
    public String URL_APPLY_PROMO = BASE_URL+"general/mobile_promocode";
    public String PROMOCODE_INFO=BASE_URL+"general/get_promo_info";
    public String TOP_AIRLINES=BASE_URL+"general/top_airlines";
    public String WALLET_DETAILS=BASE_URL+"general/get_wallet_info";
    public String ADD_WALLET_AMOUNT=BASE_URL+"user/add_wallet";

    //hotel
    public String HOTEL_SEARCH = BASE_URL+"general/pre_hotel_search_mobile";
    public String HOTEL_DETAIL_API = BASE_URL+"hotel/mobile_hotel_details";
    public String HOTEL_ROOM_LIST = BASE_URL+"hotel/get_hotel_room_list";
    public String HOTEL_ROOM_BLOCK = BASE_URL+"hotel/mobile_booking";
    public String HOTEL_ROOM_PRE_BOOK = BASE_URL+"hotel/pre_booking_api";
    public String HOTEL_TRANSACTION_HISTORY =BASE_URL+"user/hotel_transcation_history";
    public String HOTEL_CANCEL =BASE_URL+"hotel/cancel_booking_mobile";

    //bus
    public String BUS_SEARCH = BASE_URL+"general/pre_bus_search_mobile";
    public String BUS_DETAIL = BASE_URL+"bus/bus_details_mobile";
    public String BUS_PRE_BOOK = BASE_URL+"bus/pre_booking_mobile";
    public String BUS_TRANSACTION_HISTORY =BASE_URL+"user/bus_transcation_history";
    public String BUS_CANCEL =BASE_URL+"bus/cancel_booking_mobile";

    //Holiday
    public String HOLIDAY_COUNTRY_SEARCH = BASE_URL+"tours/getPackageCountry";
    public String PACKAGE_TYPE_LIST = BASE_URL+"tours/getPackageTypes";
    public String PACKAGE_DURATION = BASE_URL+"tours/getPackageDurations";
    public String PACKAGE_SEARCH = BASE_URL+"tours/search_mobile";
    public String HOLIDAY_DETAIL_API = BASE_URL+"tours/DetailMobile";
    public String SEND_HOLIDAY_QUERY = BASE_URL+"tours/enquiry_mobile";
    public String PRE_BOOKING_HOLIDAY = BASE_URL+"tours/pre_booking_itinary";

    //flight
    public String FAIR_CALENDAR = BASE_URL+"ajax/fare_list?";
    public String FAIR_RULE = BASE_URL+"flight/get_fare_details";
    public String FLIGHT_SEARCH = BASE_URL+"general/pre_flight_search_mobile";
    public String FLIGHT_DETAIL = BASE_URL+"general/flight_details_mobile";
    public String FARE_QUOTE_UPDATE = BASE_URL+"flight/booking_mobile";
    public String FLIGHT_BOOKING_URL = BASE_URL+"flight/pre_booking_mobile";
    public String FLIGHT_BOOKING_DETAIL = BASE_URL+"flight/pre_booking_mobile";
    public String FLIGHT_BOOKING_HISTORY = BASE_URL+"user/flight_transcation_history";
    public String FLIGHT_BOOKINGHistory_DETAIL = BASE_URL+"user/flight_ticket";
    public String FLIGHT_CANCEL =BASE_URL+"flight/cancel_booking_mobile";

    //transfer
    public String TRANSFER_LOC_SEARCH = BASE_URL+"transferv1/get_transfer_city_list_mobile";
    public String TRANSFERS_SEARCH = BASE_URL+"general/pre_transferv1_search_mobile";
    public String TRANSFERS_DETAIL = BASE_URL+"transferv1/mobile_transfer_details";
    public String TRANSFERS_TOUR_GRADE = BASE_URL+"transferv1/mobile_select_tourgrade";
    public String TRANSFERS_TOUR_BLOCK = BASE_URL+"transferv1/block_trip";
    public String TRANSFERS_PRE_BOOKING = BASE_URL+"transferv1/mobile_pre_booking";

    //activities
  public  static final String BOOKING_SOURCE="PTBSID0000000006";
  public static final String LOGIN_STORY = BASE_URL + "user/story_board";
  public static final String SIGHT_SEEING_CITY_SEARCH = BASE_URL + "ajax/get_sightseen_city_list";
  public static final String SIGHT_SEEING_SEARCH = BASE_URL + "general/pre_sight_seen_search";
  public static final String SIGHT_SEEING_SEARCH_LIST = BASE_URL + "ajax/sightseeing_list";
  public static final String SIGHT_SEEING_DETAILS = BASE_URL + "sightseeing/sightseeing_details";
  public static final String SELECT_TOURGRADE = BASE_URL + "sightseeing/select_tourgrade";
  public static final String BOOKING = BASE_URL + "sightseeing/booking";
  public static final String SIGHTSEEING_HISTORY = BASE_URL + "user/sightseeing_transcation_history";
  public static final String SIGHTSEEING_SHARE_HISTORY = BASE_URL + "user/sightseeing_shared_transcation_history";
  public static final String PRE_BOOKING = BASE_URL + "sightseeing/pre_booking";
  public static final String PRE_CAR_SEARCH = BASE_URL + "general/pre_car_search_mobile";



}