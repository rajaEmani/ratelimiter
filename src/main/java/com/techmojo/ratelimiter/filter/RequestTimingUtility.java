package com.techmojo.ratelimiter.filter;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 *
 */

@Component
public class RequestTimingUtility {

    public static long EXPIRED_TIME_IN_SEC = 3600;
    public static int MAX_REQUESTS = 5;

    private final Map<String, ArrayList<Date>> requestEntryMapForTenant = new HashMap<>();

    public RequestTimingUtility() {
        Timer timer = new Timer();
        timer.schedule(new CleanExpiredRequestsTask(), 0, 10 * 1000L);
    }

    /**
     * Background task that runs periodically to expire the Request count that has crossed the expiry time
     */
    class CleanExpiredRequestsTask extends TimerTask {
        public void run() {
            clearExpiredRequests();
        }
    }

    /**
     * Denoted whether a request has passed the rate limiting check or not.
     * @param tenantId is the tenantId
     * @return stating the validity of the Request
     */
    public long isValidRequest(String tenantId) {
        if (requestEntryMapForTenant.containsKey(tenantId)) {
            if (!(MAX_REQUESTS > requestEntryMapForTenant.get(tenantId).size())) {
                return fetchLeftOverTime(tenantId);
            }
        }
        addEntryInRequestMap(tenantId);
        return 0;
    }

    /**
     * Fetches the minimum left over time so that the rate limiting passes
     *
     * @param tenantId is the tennatId
     * @return if it is 0, then the request is valid
     * or else it is the time remaining before we can validly call the API
     */
    private long fetchLeftOverTime(String tenantId) {
        return EXPIRED_TIME_IN_SEC -
                TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - requestEntryMapForTenant.get(tenantId).get(0).getTime());
    }

    /**
     * Whenever a Request comes we just add its time as an Entry in the Map
     *
     * @param key is the tenantId
     */
    private void addEntryInRequestMap(String key) {
        if (requestEntryMapForTenant.containsKey(key)) {
            requestEntryMapForTenant.get(key).add(new Date());
        } else {
            ArrayList<Date> requestTimeList = new ArrayList<>();
            requestTimeList.add(new Date());
            requestEntryMapForTenant.put(key, requestTimeList);
        }
    }

    /**
     * Clears all the expired Requests per each tenant.
     */
    private void clearExpiredRequests() {
        for (Entry<String, ArrayList<Date>> entry : requestEntryMapForTenant.entrySet()) {
            ArrayList<Date> requestTimeList = entry.getValue();
            requestTimeList.removeIf(this::isExpired);
        }
    }

    /**
     * Tells us whether a request time is expired or not
     *
     * @param date is the date os the Request
     * @return stating the expiry of a RequestTime
     */
    private boolean isExpired(Date date) {
        return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - date.getTime()) > EXPIRED_TIME_IN_SEC;
    }

}