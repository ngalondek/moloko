function vg() {}
vg.INSTANCE = null;
vg.getInstance = function () {
    if (vg.INSTANCE === null) {
        vg.INSTANCE = new vg()
    }
    return vg.INSTANCE
};


/*
 * Pattern: (DAILY | WEEKLY | MONTHLY | YEARLY) INTERVAL
 * 
 * Sentence:
 * 
 * interval == 1
 * 		daily | week | month | year
 * interval != 1
 * 		interval days | weeks | months | years  
 */
vg.prototype.convertReoccurrenceSpecDesc = function (input) {
    var DAILY = "DAILY",
        FREQ = "FREQ",
        A = "INTERFACE_DF_1_MONTH",
        E = "INTERFACE_DF_1_WEEK",
        M = "INTERFACE_DF_1_YEAR",
        Q = "INTERFACE_DF_NUM_MONTHS",
        D = "INTERFACE_DF_NUM_WEEKS",
        K = "INTERFACE_DF_NUM_YEARS",
        H = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_DAYS",
        I = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_DAY",
        INTERVAL = "INTERVAL",
        MONTHLY = "MONTHLY",
        WEEKLY = "WEEKLY",
        YEARLY = "YEARLY";
    var interval = input[INTERVAL] ? input[INTERVAL] : 1;
    var W;
    switch (input[FREQ]) {
    case DAILY:
        if (interval == 1) {
            return text(I)
        } else {
            return text(H, {
                NUM: interval
            })
        }
        break;
    case WEEKLY:
        if (interval == 1) {
            return text(E)
        } else {
            return text(D, {
                NUM: interval
            })
        }
        break;
    case MONTHLY:
        if (interval == 1) {
            return text(A)
        } else {
            return text(Q, {
                NUM: interval
            })
        }
        break;
    case YEARLY:
        if (interval == 1) {
            return text(M)
        } else {
            return text(K, {
                NUM: interval
            })
        }
        break
    }
};

/*
 * Pattern: WEEKLY INTERVAL (BYDAY | (BYDAY,BYDAY)+)
 * 
 * Sentence:
 * 
 * interval == 1
 * 		interval days
 * interval != 1
 * 		days
 */
vg.prototype.convertReoccurrenceWeeklyDesc = function (input) {
    var EMPTY = "",
        SPACE = " ",
        COMMA = ",",
        COMMA_SPACE = ", ",
        BYDAY = "BYDAY",
        FR = "FR",
        Z = "INTERFACE_DF_WEEKDAY",
        INTERVAL = "INTERVAL",
        MO = "MO",
        TH = "TH",
        TU = "TU",
        WE = "WE",
        W = "ja",
        X = "zh-CN",
        Y = "zh-TW";
    var interval = input[INTERVAL] ? input[INTERVAL] : 1;
    var byDayValues = input[BYDAY].split(COMMA);
    var dayLiterals = [];
    for (var D = 0; D < byDayValues.length; D++) {
        dayLiterals.push(Intl.day_wide[Date.ICALDays[byDayValues[D]]])
    }
    if (interval != 1) {
        interval = utility.suffixize(interval) + SPACE
    } else {
        interval = EMPTY;
        if (dayLiterals.length == 5) {
            var workdaysCount = 0;
            for (var D = 0; D < byDayValues.length; D++) {
                if (workdaysCount == 5) {
                    break
                }
                switch (byDayValues[D]) {
                case MO:
                case TU:
                case WE:
                case TH:
                case FR:
                    workdaysCount++;
                default:
                }
            }
            if (workdaysCount == 5) {
                return text(Z)
            }
        }
    }
    
    if (rtmLanguage == Y || (rtmLanguage == X || rtmLanguage == W)) {
        return interval + dayLiterals.join(this.getDayJoin())
    }
    
    return interval + dayLiterals.join(COMMA_SPACE)
};


/*
 * Pattern: MONTHLY INTERVAL (BYDAY | (BYDAY,BYDAY)+)
 * 
 * Sentence:
 * 
 * interval == 1
 * 		month on the (daynum | last | daynum last) days
 * interval != 1
 * 		interval month on the (daynum | last | daynum last) days 
 */
vg.prototype.convertReoccurrenceDayOfMonthDesc = function (input) {
    var SPACE = " ",
        LAST = " last ",
        ON_THE_MONTH = " of the month",
        ON_THE = " on the ",
        COMMA = ",",
        COMMA_SPACE = ", ",
        MINUS = "-",
        BYDAY = "BYDAY",
        INTERVAL = "INTERVAL",
        I = "ja",
        LAST_SPACE = "last ",
        MONTH_ON_THE = "month on the ",
        J = "zh-CN",
        K = "zh-TW";
    var interval = input[INTERVAL] ? input[INTERVAL] * 1 : 1;
    var lastDayNum = 1;
    var dayValues = input[BYDAY].split(COMMA);
    var daysLiteral = [];
    for (var D = 0; D < dayValues.length; D++) {
        if (dayValues[D].charAt(0) == MINUS) {
            lastDayNum = dayValues[D].substring(0, 2) * 1;
            dayValues[D] = dayValues[D].substring(2)
        } else {
            lastDayNum = dayValues[D].substring(0, 1) * 1;
            dayValues[D] = dayValues[D].substring(1)
        }
        daysLiteral.push(Intl.day_wide[Date.ICALDays[dayValues[D]]])
    }
    if (lastDayNum < 0) {
        if (lastDayNum == -1) {
            lastDayNum = LAST_SPACE
        } else {
            lastDayNum = utility.suffixize(lastDayNum * -1) + LAST
        }
    } else {
        lastDayNum = utility.suffixize(lastDayNum) + SPACE
    } if (rtmLanguage == K || (rtmLanguage == J || rtmLanguage == I)) {
        return lastDayNum + daysLiteral.join(this.getDayJoin()) + ON_THE_MONTH
    }
    
    if (interval === 1) {
        return MONTH_ON_THE + lastDayNum + daysLiteral.join(COMMA_SPACE)
    } else {
        return this.convertReoccurrenceSpecDesc(input) + ON_THE + lastDayNum + daysLiteral.join(COMMA_SPACE)
    }
};

/*
 * Pattern: YEARLY BYMONTH (BYDAY | (BYDAY,BYDAY)+)
 * 
 * Sentence:
 * 
 * 	 year on the (daynum | last | daynum last) days in month
 */
vg.prototype.convertReoccurrenceYearlyWeekDesc = function (input) {
    var SPACE = " ",
        IN = " in ",
        LAST = " last ",
        COMMA = ",",
        MINUS = "-",
        BYDAY = "BYDAY",
        BYMONTH = "BYMONTH",
        LAST_SPACE = "last ",
        YEAR_ON_THE = "year on the ";
    var dayValues = input[BYDAY].split(COMMA);
    var dayLiterals = [];
    for (var D = 0; D < dayValues.length; D++) {
        if (dayValues[D].charAt(0) == MINUS) {
            lastDayNum = dayValues[D].substring(0, 2) * 1;
            dayValues[D] = dayValues[D].substring(2)
        } else {
            lastDayNum = dayValues[D].substring(0, 1) * 1;
            dayValues[D] = dayValues[D].substring(1)
        }
        dayLiterals.push(Intl.day_wide[Date.ICALDays[dayValues[D]]])
    }
    if (lastDayNum < 0) {
        if (lastDayNum == -1) {
            lastDayNum = LAST_SPACE
        } else {
            lastDayNum = utility.suffixize(lastDayNum * -1) + LAST
        }
    } else {
        lastDayNum = utility.suffixize(lastDayNum) + SPACE
    }
    var monthLiteral = Intl.month_wide[input[BYMONTH] * 1 - 1];
    return YEAR_ON_THE + lastDayNum + dayLiterals.join(SPACE) + IN + monthLiteral
};

vg.RE_DAYJOIN = new RegExp("{DAY_NAME}", "g");

vg.prototype.getDayJoin = function () {
    var A = "",
        B = "INTERFACE_DF_DAY_NAME_AND_DAY_NAME";
    var D = _T(B);
    D = D.replace(vg.RE_DAYJOIN, A).trim();
    return D
};

/*
 * Pattern: MONTHLY BYMONTHDAY
 * 
 * Sentence:
 * 
 * 	 month on the month days
 */
vg.prototype.convertReoccurrenceMonthlyDesc = function (input) {
    var EMPTY = "",
        COMMA_SPACE = ", ",
        BYMONTHDAY = "BYMONTHDAY",
        MONTH_ON_THE = "month on the ";
    var monthDaysValue = input[BYMONTHDAY];
    var monthDays = monthDaysValue ? monthDaysValue.split(/\,/g) : [monthDaysValue];
    monthDays = map(function (K) {
        var monthDayNum = EMPTY + K;
        monthDayNum = monthDayNum.trim();
        return utility.suffixize(monthDayNum)
    }, monthDays);
    return MONTH_ON_THE + monthDays.join(COMMA_SPACE)
};

vg.prototype.convertReoccurrenceDesc = function (F, K) {
    var SEMI = ";",
        EQUALS = "=",
        BYDAY = "BYDAY",
        BYMONTH = "BYMONTH",
        BYMONTHDAY = "BYMONTHDAY",
        COUNT = "COUNT",
        FREQ = "FREQ",
        AA = "INTERFACE_DF_SEMITER_INTERVAL",
        M = "INTERFACE_DF_EVERY_DAY_NAME_FOR_NUM_TIMES",
        AC = "INTERFACE_DF_EVERY_DAY_NAME_UNTIL_DATE",
        AB = "INTERFACE_DF_EVERY_INTERVAL",
        MONTHLY = "MONTHLY",
        UNTIL = "UNTIL",
        YEARLY = "YEARLY";
    var E = F[0];
    var O = F[1];
    var res;
    var operators = E.split(SEMI);
    var operator2Value = {};
    for (var J = 0; J < operators.length; J++) {
        var I = operators[J].split(EQUALS);
        operator2Value[I[0]] = I[1]
    }
    if (BYDAY in operator2Value && (BYMONTH in operator2Value && operator2Value[FREQ] === YEARLY)) {
        res = this.convertReoccurrenceYearlyWeekDesc(operator2Value)
    } else {
        if (is(operator2Value[BYDAY])) {
            if (operator2Value[FREQ] == MONTHLY) {
                res = this.convertReoccurrenceDayOfMonthDesc(operator2Value)
            } else {
                res = this.convertReoccurrenceWeeklyDesc(operator2Value)
            }
        } else {
            if (is(operator2Value[BYMONTHDAY])) {
                res = this.convertReoccurrenceMonthlyDesc(operator2Value)
            } else {
                res = this.convertReoccurrenceSpecDesc(operator2Value)
            }
        }
    } if (O) {
        if (is(operator2Value[COUNT])) {
            return _T(M, {
                DAY_NAME: res,
                NUM: operator2Value[COUNT]
            })
        } else {
            if (is(operator2Value[UNTIL])) {
                return _T(AC, {
                    DAY_NAME: res,
                    DATE: this.formatUntil(operator2Value[UNTIL], !! K)
                })
            } else {
                return _T(AB, {
                    INTERVAL: res
                })
            }
        }
    } else {
        return _T(AA, {
            INTERVAL: res
        })
    }
};
vg.prototype.formatUntil = function (D, A) {
    var B = Date.fromISO(D);
    var E = !(B.getHours() === 0 && B.getMinutes() === 0);
    if (E) {
        if (A) {
            return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_time_field, B.getTime() / 1000)
        } else {
            return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_time, B.getTime() / 1000)
        }
    } else {
        if (A) {
            return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_field, B.getTime() / 1000)
        } else {
            return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due, B.getTime() / 1000)
        }
    }
};
vg.prototype.parseReoccurrence = function (M) {
    var AZ = "$1 of the month",
        Ac = "2 weeks",
        Ae = "annually",
        AO = "biweekly",
        Ag = "daily",
        AR = "day_of_month",
        AY = "each",
        AX = "every",
        AL = "every 2 weeks",
        AV = "every day",
        V = "every month",
        Y = "every week",
        Ad = "every year",
        AM = "fortnightly",
        Ab = "mon,tue,wed,thu,fri",
        AW = "mont",
        W = "monthly",
        Ah = "never",
        AU = "of",
        Aa = "sat,sun",
        AP = "spec",
        AJ = "weekly",
        Af = "yearly",
        AT = "yearly_week";
    var O = null;
    if (!M || (M.trim().length == 0 || M.trim() == Ah)) {
        return null
    }
    M = M.toLowerCase();
    if (M.indexOf(Ag) > -1) {
        M = AV
    } else {
        if (M.indexOf(AO) > -1 || M.indexOf(AM) > -1) {
            M = AL
        } else {
            if (M.indexOf(AJ) > -1) {
                M = Y
            } else {
                if (M.indexOf(W) > -1) {
                    M = V
                } else {
                    if (M.indexOf(Af) > -1 || M.indexOf(Ae) > -1) {
                        M = Ad
                    }
                }
            }
        }
    }
    M = M.replace(/fortnight/i, Ac);
    M = M.replace(/biweekly/i, Ac);
    M = M.replace(/week\s?day/i, Ab);
    M = M.replace(/week\s?end/i, Aa);
    M = M.replace(/month\s*on\s*the\s*(\S*)$/i, AZ);
    M = M.replace(/month\s*on\s*the\s*(.*)/i, AZ);
    var AF = /(every|after)?\s*(([0-9]+)\s*(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)\s*([0-9]*))/i;
    var Q = /(every|after)?\s*(?:year\s*on\s*the)?\s*(first|1st|second|2nd|third|3rd|fourth|4th|fifth|5th|last|[1-5])\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun)\s*in\s*(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)/i;
    var K = /(every|after)?\s*([0-9]+)(th|st|nd|rd)/i;
    var AS = /(every|after)?\s*((first|1st|second|2nd|third|3rd|fourth|4th|other|last|[1-5])?\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun))/i;
    var AQ = /(every|after)?\s*((one|two|three|four|five|six|seven|eight|nine|ten|other|[0-9]+)\s*)?(years|year|yrs|yr|months|month|mons|mon|weeks|week|wks|wk|days|day)/i;
    var AK = /(every|after)?\s*(first|1st|second|2nd|third|3rd|fourth|4th|fifth|5th|last|[1-5])(\s*last)?\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun)/i;
    var AE = /until\s*(.*)+\s*/;
    var J = /for\s*([0-9]+).*/;
    var AD = M;
    var AH = AE.exec(M);
    var Ai = J.exec(M);
    if (AH) {
        M = M.substring(0, M.indexOf(AH[0]));
        AH = dateTimeMgr.parseDueDate(AH[1])
    } else {
        if (Ai) {
            M = M.substring(0, M.indexOf(Ai[0]));
            Ai = Ai[1]
        }
    }
    var I = AF.exec(M);
    var AI = Q.exec(M);
    var AG = K.exec(M);
    var AA = AS.exec(M);
    var Z = AQ.exec(M);
    var X = AK.exec(M);
    var H = M.indexOf(AY) > -1 || M.indexOf(AX) > -1 ? true : false;
    if (H === false) {
        Ai = null;
        AH = null
    }
    var AC = M.indexOf(AW) > -1;
    var AN = M.indexOf(AU) > -1;
    if (AG && AA) {
        if (AC) {
            AA = null;
            if (X) {
                AG = null
            }
        } else {
            X = null;
            AG = null
        }
    }
    if (Z && (AA && !AC)) {
        Z = null
    } else {
        if (Z && (AA && AC)) {
            AA = null
        }
    } if (Z && (X && AN)) {
        Z = null
    } else {
        if (Z && (X && !AN)) {
            X = null
        }
    } if (AI && Z) {
        Z = null
    }
    if (X && (AA && !AC)) {
        X = null
    }
    if (AI) {
        O = [AT, AI, AH, Ai, H]
    } else {
        if (I) {
            O = [Af, I, AH, Ai, H]
        } else {
            if (X) {
                var R = M.split(/\s*(?:and|\,)\s*/);
                var U = [];
                U.push(X);
                if (R.length > 1) {
                    for (var AB = 1; AB < R.length; AB++) {
                        U.push(AK.exec(R[AB]))
                    }
                }
                X = U;
                O = [AR, X, AH, Ai, H]
            } else {
                if (AG) {
                    var R = M.split(/\s*(?:and|\,)\s*/);
                    var U = [];
                    U.push(AG);
                    if (R.length > 1) {
                        for (var AB = 1; AB < R.length; AB++) {
                            U.push(K.exec(R[AB]))
                        }
                    }
                    AG = U;
                    O = [W, AG, AH, Ai, H]
                } else {
                    if (AA) {
                        var R = M.split(/\s*(?:and|\,)\s*/);
                        var U = [];
                        U.push(AA);
                        if (R.length > 1) {
                            for (var AB = 1; AB < R.length; AB++) {
                                U.push(AS.exec(R[AB]))
                            }
                        }
                        AA = U;
                        O = [AJ, AA, AH, Ai, H]
                    } else {
                        if (Z) {
                            var R = M.split(/\s*(?:and|\,)\s*/);
                            var U = [];
                            U.push(Z);
                            if (R.length > 1) {
                                for (var AB = 1; AB < R.length; AB++) {
                                    U.push(AQ.exec(R[AB]))
                                }
                            }
                            Z = U;
                            O = [AP, Z, AH, Ai, H]
                        }
                    }
                }
            }
        }
    } if (O !== null) {
        O.push(AD)
    }
    return O
};