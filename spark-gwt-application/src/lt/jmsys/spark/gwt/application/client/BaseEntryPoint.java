package lt.jmsys.spark.gwt.application.client;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.application.AlcsActivityManager;
import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import lt.jmsys.spark.gwt.application.common.client.helper.FileUploadHelper;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import lt.jmsys.spark.gwt.client.suggest.oracle.TransliterationHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.DateField;
import lt.jmsys.spark.gwt.client.ui.form.field.DecimalField;
import lt.jmsys.spark.gwt.client.ui.form.field.calendar.Calendar;
import lt.jmsys.spark.gwt.client.ui.form.field.calendar.CalendarConstants;
import lt.jmsys.spark.gwt.client.ui.form.i18n.DateFormatter;
import lt.jmsys.spark.gwt.client.ui.form.i18n.NumberFormatter;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import eu.itreegroup.spark.application.shared.search.TransliterationTable;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BaseEntryPoint implements EntryPoint {

    protected final static String APPLICATION_PAGE_CONTAINER_ID = "pageContentContainer";

    protected ClientFactory clientFactory;
    protected ApplicationPresenter applicationPresenter;
    protected ActivityManager activityManager;
    protected PlaceHistoryHandler placeHistoryHandler;

    /**
     * This is the entry point method.
     */

    @Override
    public void onModuleLoad() {
        init();
    }

    protected void init() {
        initDefaults();
        initClientFactory();
        initApplicationPresenter();
        initActivityManager();
        initPlaceHistoryHandler();
        initPreloadSessionData();
    }

    protected void initClientFactory() {
        ClientFactory.init();
        clientFactory = ClientFactory.getInstance();
    }

    protected void initApplicationPresenter() {
        applicationPresenter = clientFactory.getApplicationPresenter();
    }

    protected void initActivityManager() {
        activityManager = new AlcsActivityManager(clientFactory.getEventBus());
        activityManager.setDisplay(applicationPresenter.getView().getFormPlaceholder());
    }

    protected void initPlaceHistoryHandler() {
        placeHistoryHandler = clientFactory.getHistoryHandler();
    }

    @SuppressWarnings("deprecation")
    protected void setupPlaceHistoryHandler() {
        placeHistoryHandler.register(clientFactory.getPlaceController(), clientFactory.getEventBus(), applicationPresenter.getFirstPlace());
        placeHistoryHandler.handleCurrentHistory();
    }

    protected void initPreloadSessionData() {
        applicationPresenter.preloadSessionData(new AsyncCallback<PreloadedSessionData>() {

            @Override
            public void onSuccess(PreloadedSessionData result) {
                onSessionDataPreload(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Logger.getLogger(BaseEntryPoint.class.getName()).log(Level.SEVERE, "Cannot initialize application", caught);
            }
        }, false);
    }

    protected void onSessionDataPreload(PreloadedSessionData preloadedSessionData) {
        initTransliteration();
        initApplicationPageBody();
        setupPlaceHistoryHandler();
    }

    protected void initTransliteration() {
        TransliterationHelper.init(TransliterationTable.map());
    }

    protected void initApplicationPageBody() {
        DOM.getElementById(APPLICATION_PAGE_CONTAINER_ID).setInnerHTML("");
        RootPanel.get(APPLICATION_PAGE_CONTAINER_ID).add(applicationPresenter.getView());
    }

    public static void initDefaults() {
        FileUploadHelper.initDefaults();
        initDateDefaults();
        initNumberDefaults();
    }

    private static void initNumberDefaults() {
        NumberFormatter.setDecimalFormat(new DecimalField.DecimalFormat("#,##0.00"));
        NumberFormatter.setIntegerFormat(new DecimalField.DecimalFormat("#,##0"));
    }

    private static void initDateDefaults() {
        final Date now = new Date();
        final Date minDate = DateFormatter.newDate(2010, 1, 1);
        @SuppressWarnings("deprecation")
        final Date maxDate = DateFormatter.newDate((now.getYear() + 1900) + 4, 1, 1);
        DateField.setDefaultMinDate(minDate);
        DateField.setDefaultMaxDate(maxDate);
        DateFormatter.setDateFormat(DateTimeFormat.getFormat("yyyy-MM-dd"));
        DateFormatter.setDateFormatDateAndFullTime(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
        DateFormatter.setDateFormatDateAndHours(DateTimeFormat.getFormat("yyyy-MM-dd HH"));
        DateFormatter.setDateFormatDateAndShortTime(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm"));
        DateFormatter.setDateFormatYearAndMonth(DateTimeFormat.getFormat("yyyy.MM"));

        Calendar.setConstants(new CalendarConstants() {

            CalendarConstants calendarConstants = GWT.create(CalendarConstants.class);

            @Override
            public String firstDayOfWeek() {
                return calendarConstants.firstDayOfWeek();
            }

            @Override
            public String sun() {
                return calendarConstants.sun();
            }

            @Override
            public String mon() {
                return calendarConstants.mon();
            }

            @Override
            public String tue() {
                return calendarConstants.tue();
            }

            @Override
            public String wed() {
                return calendarConstants.wed();
            }

            @Override
            public String thu() {
                return calendarConstants.thu();
            }

            @Override
            public String fri() {
                return calendarConstants.fri();
            }

            @Override
            public String sat() {
                return calendarConstants.sat();
            }

            @Override
            public String january() {
                return calendarConstants.january();
            }

            @Override
            public String february() {
                return calendarConstants.february();
            }

            @Override
            public String march() {
                return calendarConstants.march();
            }

            @Override
            public String april() {
                return calendarConstants.april();
            }

            @Override
            public String may() {
                return calendarConstants.may();
            }

            @Override
            public String june() {
                return calendarConstants.june();
            }

            @Override
            public String july() {
                return calendarConstants.july();
            }

            @Override
            public String august() {
                return calendarConstants.august();
            }

            @Override
            public String september() {
                return calendarConstants.september();
            }

            @Override
            public String october() {
                return calendarConstants.october();
            }

            @Override
            public String november() {
                return calendarConstants.november();
            }

            @Override
            public String december() {
                return calendarConstants.december();
            }

            @Override
            public String hoursCircleBasis() {
                return calendarConstants.hoursCircleBasis();
            }

            @Override
            public String dateTimeHoursFormat() {
                return calendarConstants.dateTimeHoursFormat();
            }

            @Override
            public String dateTimeMinutesFormat() {
                return calendarConstants.dateTimeMinutesFormat();
            }

            @Override
            public String dateTimeSecondsFormat() {
                return calendarConstants.dateTimeSecondsFormat();
            }

            @Override
            public String dateFormat() {
                return DateFormatter.getDateFormat().getPattern();
            }

            @Override
            public String chooseTime() {
                return calendarConstants.chooseTime();
            }

            @Override
            public String titleNextYear() {
                return calendarConstants.titleNextYear();
            }

            @Override
            public String today() {
                return calendarConstants.today();
            }

            @Override
            public String titleNextMonth() {
                return calendarConstants.titleNextMonth();
            }

            @Override
            public String titlePreviousYear() {
                return calendarConstants.titlePreviousYear();
            }

            @Override
            public String titlePreviousMonth() {
                return calendarConstants.titlePreviousMonth();
            }

        });

    }

}
