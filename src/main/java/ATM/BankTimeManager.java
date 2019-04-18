package ATM;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BankTimeManager implements Serializable {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy MM dd");
    private final SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat customDates = new SimpleDateFormat("yyyy MM dd HH:mm");
    private Date bankDate;

    /**
     * Set ths date of this Bank.
     *
     * @param date The date to set.
     */
    public void setDate(Date date) {
        bankDate = date;
    }

    /**
     * Gets the desired format of dates of this bank
     *
     * @return SimpleDateFormat object
     */
    public SimpleDateFormat getDateFormat() {
        return dayFormat;
    }

    /**
     * Get the current date and time of this bank.
     *
     * @return the current date and time.
     */
    public Date getCurrentTime() {
        Date currentTime = new Date();
        try {
            String currentDay = dayFormat.format(bankDate);
            String currentHrMin = hourMinuteFormat.format(new Date());
            currentTime = customDates.parse(currentDay + " " + currentHrMin);
        } catch (Exception e) {
            System.out.println("Warning: The date has not been set. The system date has been used instead.");
        }
        return currentTime;
    }

    /**
     * Increment the date of this bank by 1 day.
     */
    void updateDate() {
        if (bankDate == null) {
            bankDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(bankDate);
        c.add(Calendar.DATE, 1);
        bankDate = c.getTime();
    }

    /**
     * Check whether it is the start of the month.
     *
     * @return true if it is the first day of the month, false otherwise.
     */
    boolean isStartOfMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(getCurrentTime());
        return c.get(Calendar.DAY_OF_MONTH) == 1;
    }
}
