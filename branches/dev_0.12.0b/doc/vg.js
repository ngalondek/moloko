function vg()
{
}
vg.INSTANCE = null;
vg.getInstance = function ()
{
   if (vg.INSTANCE === null)
   {
      vg.INSTANCE = new vg()
   }
   return vg.INSTANCE
};
vg.prototype.convertReoccurrenceSpecDesc = function (V)
{
   var J = "DAILY",
      R = "FREQ",
      A = "INTERFACE_DF_1_MONTH",
      E = "INTERFACE_DF_1_WEEK",
      M = "INTERFACE_DF_1_YEAR",
      Q = "INTERFACE_DF_NUM_MONTHS",
      D = "INTERFACE_DF_NUM_WEEKS",
      K = "INTERFACE_DF_NUM_YEARS",
      H = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_DAYS",
      I = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_DAY",
      U = "INTERVAL",
      B = "MONTHLY",
      F = "WEEKLY",
      O = "YEARLY";
   var X = V[U] ? V[U] : 1;
   var W;
   switch (V[R])
   {
   case J:
      if (X == 1)
      {
         return _T(I)
      }
      else
      {
         return _T(H, {
            NUM: X
         })
      }
      break;
   case F:
      if (X == 1)
      {
         return _T(E)
      }
      else
      {
         return _T(D, {
            NUM: X
         })
      }
      break;
   case B:
      if (X == 1)
      {
         return _T(A)
      }
      else
      {
         return _T(Q, {
            NUM: X
         })
      }
      break;
   case O:
      if (X == 1)
      {
         return _T(M)
      }
      else
      {
         return _T(K, {
            NUM: X
         })
      }
      break
   }
};
vg.prototype.convertReoccurrenceWeeklyDesc = function (A)
{
   var Q = "",
      R = " ",
      U = ",",
      V = ", ",
      AA = "BYDAY",
      I = "FR",
      Z = "INTERFACE_DF_WEEKDAY",
      AB = "INTERVAL",
      O = "MO",
      J = "TH",
      M = "TU",
      K = "WE",
      W = "ja",
      X = "zh-CN",
      Y = "zh-TW";
   var H = A[AB] ? A[AB] : 1;
   var F = A[AA].split(U);
   var E = [];
   for (var D = 0; D < F.length; D++)
   {
      E.push(Intl.day_wide[Date.ICALDays[F[D]]])
   }
   if (H != 1)
   {
      H = utility.suffixize(H) + R
   }
   else
   {
      H = Q;
      if (E.length == 5)
      {
         var B = 0;
         for (var D = 0; D < F.length;
         D++)
         {
            if (B == 5)
            {
               break
            }
            switch (F[D])
            {
            case O:
            case M:
            case K:
            case J:
            case I:
               B++;
            default:
            }
         }
         if (B == 5)
         {
            return _T(Z)
         }
      }
   }
   if (rtmLanguage == Y || (rtmLanguage == X || rtmLanguage == W))
   {
      return H + E.join(this.getDayJoin())
   }
   return H + E.join(V)
};
vg.prototype.convertReoccurrenceDayOfMonthDesc = function (A)
{
   var M = " ",
      O = " last ",
      Y = " of the month",
      V = " on the ",
      U = ",",
      W = ", ",
      R = "-",
      Z = "BYDAY",
      z = "INTERVAL",
      I = "ja",
      Q = "last ",
      X = "month on the ",
      J = "zh-CN",
      K = "zh-TW";
   var B = A[z] ? A[z] * 1 : 1;
   var H = 1;
   var F = A[Z].split(U);
   var E = [];
   for (var D = 0; D < F.length; D++)
   {
      if (F[D].charAt(0) == R)
      {
         H = F[D].substring(0, 2) * 1;
         F[D] = F[D].substring(2)
      }
      else
      {
         H = F[D].substring(0, 1) * 1;
         F[D] = F[D].substring(1)
      }
      E.push(Intl.day_wide[Date.ICALDays[F[D]]])
   }
   if (H < 0)
   {
      if (H == -1)
      {
         H = Q
      }
      else
      {
         H = utility.suffixize(H * -1) + O
      }
   }
   else
   {
      H = utility.suffixize(H) + M
   }
   if (rtmLanguage == K || (rtmLanguage == J || rtmLanguage == I))
   {
      return H + E.join(this.getDayJoin()) + Y
   }
   if (B === 1)
   {
      return X + H + E.join(W)
   }
   else
   {
      return this.convertReoccurrenceSpecDesc(A) + V + H + E.join(W)
   }
};
vg.prototype.convertReoccurrenceYearlyWeekDesc = function (opHash)
{
   var K = " ",
      H = " in ",
      M = " last ",
      R = ",",
      Q = "-",
      U = "BYDAY",
      J = "BYMONTH",
      O = "last ",
      I = "year on the ";
   var F = opHash[U].split(R);
   var E = [];
   for (var D = 0; D < F.length; D++)
   {
      if (F[D].charAt(0) == Q)
      {
         count = F[D].substring(0, 2) * 1;
         F[D] = F[D].substring(2)
      }
      else
      {
         count = F[D].substring(0, 1) * 1;
         F[D] = F[D].substring(1)
      }
      E.push(Intl.day_wide[Date.ICALDays[F[D]]])
   }
   if (count < 0)
   {
      if (count == -1)
      {
         count = O
      }
      else
      {
         count = utility.suffixize(count * -1) + M
      }
   }
   else
   {
      count = utility.suffixize(count) + K
   }
   var B = Intl.month_wide[opHash[J] * 1 - 1];
   return I + count + E.join(K) + H + B
};
vg.RE_DAYJOIN = new RegExp("{DAY_NAME}", "g");
vg.prototype.getDayJoin = function ()
{
   var A = "",
      B = "INTERFACE_DF_DAY_NAME_AND_DAY_NAME";
   var D = _T(B);
   D = D.replace(vg.RE_DAYJOIN, A).trim();
   return D
};
vg.prototype.convertReoccurrenceMonthlyDesc = function (A)
{
   var H = "",
      E = ", ",
      I = "BYMONTHDAY",
      F = "month on the ";
   var D = A[I];
   var B = D ? D.split(/\,/g) : [D];
   B = map(function (K)
   {
      var J = H + K;
      J = J.trim();
      return utility.suffixize(J)
   }, B);
   return F + B.join(E)
};
vg.prototype.convertReoccurrenceDesc = function (pattern, afterFlag)
{
   var AF = ";",
      AE = "=",
      BYDAY = "BYDAY",
      BYMONTH = "BYMONTH",
      BYMONTHDAY = "BYMONTHDAY",
      COUNT = "COUNT",
      FREQ = "FREQ",
      INTERFACE_DF_AFTER_INTERVAL = "INTERFACE_DF_AFTER_INTERVAL",
      INTERFACE_DF_EVERY_DAY_NAME_FOR_NUM_TIMES = "INTERFACE_DF_EVERY_DAY_NAME_FOR_NUM_TIMES",
      INTERFACE_DF_EVERY_DAY_NAME_UNTIL_DATE = "INTERFACE_DF_EVERY_DAY_NAME_UNTIL_DATE",
      INTERFACE_DF_EVERY_INTERVAL = "INTERFACE_DF_EVERY_INTERVAL",
      MONTHLY = "MONTHLY",
      UNTIL = "UNTIL",
      YEARLY = "YEARLY";
   var E = pattern[0];
   var O = pattern[1];
   var D;
   var operator = E.split(AF);
   var opHash =
   {
   };
   for (var J = 0; J < operator.length; J++)
   {
      var I = operator[J].split(AE);
      opHash[I[0]] = I[1]
   }
   if (BYDAY in opHash && (BYMONTH in opHash && opHash[FREQ] === YEARLY))
   {
      D = this.convertReoccurrenceYearlyWeekDesc(opHash)
   }
   else
   {
      if (is(opHash[BYDAY]))
      {
         if (opHash[FREQ] == MONTHLY)
         {
            D = this.convertReoccurrenceDayOfMonthDesc(opHash)
         }
         else
         {
            D = this.convertReoccurrenceWeeklyDesc(opHash)
         }
      }
      else
      {
         if (is(opHash[BYMONTHDAY]))
         {
            D = this.convertReoccurrenceMonthlyDesc(opHash)
         }
         else
         {
            D = this.convertReoccurrenceSpecDesc(opHash)
         }
      }
   }
   if (O)
   {
      if (is(opHash[COUNT]))
      {
         return _T(INTERFACE_DF_EVERY_DAY_NAME_FOR_NUM_TIMES, {
            DAY_NAME: D,
            NUM: opHash[COUNT]
         })
      }
      else
      {
         if (is(opHash[UNTIL]))
         {
            return _T(INTERFACE_DF_EVERY_DAY_NAME_UNTIL_DATE, {
               DAY_NAME: D,
               DATE: this.formatUntil(opHash[UNTIL], !! afterFlag)
            })
         }
         else
         {
            return _T(INTERFACE_DF_EVERY_INTERVAL, {
               INTERVAL: D
            })
         }
      }
   }
   else
   {
      return _T(INTERFACE_DF_AFTER_INTERVAL, {
         INTERVAL: D
      })
   }
};
vg.prototype.formatUntil = function (D, A)
{
   var B = Date.fromISO(D);
   var E = !(B.getHours() === 0 && B.getMinutes() === 0);
   if (E)
   {
      if (A)
      {
         return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_time_field, B.getTime() / 1000)
      }
      else
      {
         return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_time, B.getTime() / 1000)
      }
   }
   else
   {
      if (A)
      {
         return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due_field, B.getTime() / 1000)
      }
      else
      {
         return dateTimeMgr.getCachedIntlFormat(Intl.preferred_formats.due, B.getTime() / 1000)
      }
   }
};
vg.prototype.parseReoccurrence = function (input)
{
   var Xst_of_M = "$1 of the month",
      _2_WEEKS = "2 weeks",
      ANNUALAYLY = "annually",
      BIWEEKLY = "biweekly",
      DAILY = "daily",
      DAY_OF_MONTH = "day_of_month",
      EACH = "each",
      EVERY = "every",
      EVERY_2_WEEKS = "every 2 weeks",
      EVERY_DAY = "every day",
      EVERY_MONTH = "every month",
      EVERY_WEEK = "every week",
      EVERY_YEAR = "every year",
      FORTNIGHTLY = "fortnightly",
      MON_TILL_FRI = "mon,tue,wed,thu,fri",
      MONT = "mont",
      MONTHLY = "monthly",
      NEVER = "never",
      OF = "of",
      SAT_SUN = "sat,sun",
      SPEC = "spec",
      WEEKLY = "weekly",
      YEARLY = "yearly",
      YEARLY_WEEK = "yearly_week";
   var output = null;
   if (!input || (input.trim().length == 0 || input.trim() == NEVER))
   {
      return null
   }
   input = input.toLowerCase();
   if (input.indexOf(DAILY) > -1)
   {
      input = EVERY_DAY
   }
   else
   {
      if (input.indexOf(BIWEEKLY) > -1 || input.indexOf(FORTNIGHTLY) > -1)
      {
         input = EVERY_2_WEEKS
      }
      else
      {
         if (input.indexOf(WEEKLY) > -1)
         {
            input = EVERY_WEEK
         }
         else
         {
            if (input.indexOf(MONTHLY) > -1)
            {
               input = EVERY_MONTH
            }
            else
            {
               if (input.indexOf(YEARLY) > -1 || input.indexOf(ANNUALLY) > -1)
               {
                  input = EVERY_YEAR
               }
            }
         }
      }
   }
   input = input.replace(/fortnight/i, _2_WEEKS);
   input = input.replace(/biweekly/i, _2_WEEKS);
   input = input.replace(/week\s?day/i, MON_TILL_FRI);
   input = input.replace(/week\s?end/i, SAT_SUN);
   input = input.replace(/month\s*on\s*the\s*(\S*)$/i, Xst_of_M);
   input = input.replace(/month\s*on\s*the\s*(.*)/i, Xst_of_M);
   var X_M_Y = /(every|after)?\s*(([0-9]+)\s*(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)\s*([0-9]*))/i;
   var Y_Xst_WD_in_M = /(every|after)?\s*(?:year\s*on\s*the)?\s*(first|1st|second|2nd|third|3rd|fourth|4th|fifth|5th|last|[1-5])\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun)\s*in\s*(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)/i;
   var Xst = /(every|after)?\s*([0-9]+)(th|st|nd|rd)/i;
   var Xst_WD = /(every|after)?\s*((first|1st|second|2nd|third|3rd|fourth|4th|other|last|[1-5])?\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun))/i;
   var X_YMD = /(every|after)?\s*((one|two|three|four|five|six|seven|eight|nine|ten|other|[0-9]+)\s*)?(years|year|yrs|yr|months|month|mons|mon|weeks|week|wks|wk|days|day)/i;
   var Xst_last_WD = /(every|after)?\s*(first|1st|second|2nd|third|3rd|fourth|4th|fifth|5th|last|[1-5])(\s*last)?\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun)/i;
   var AE = /until\s*(.*)+\s*/;
   var J = /for\s*([0-9]+).*/;
   var AD = input;
   var match_Until = AE.exec(input);
   var match_For = J.exec(input);
   if (match_Until)
   {
      input = input.substring(0, input.indexOf(match_Until[0]));
      match_Until = dateTimeMgr.parseDueDate(match_Until[1])
   }
   else
   {
      if (match_For)
      {
         input = input.substring(0, input.indexOf(match_For[0]));
         match_For = match_For[1]
      }
   }
   var match_X_M_Y = X_M_Y.exec(input);
   var match_Y_Xst_WD_in_M = Y_Xst_WD_in_M.exec(input);
   var match_Xst = Xst.exec(input);
   var match_Xst_WD = Xst_WD.exec(input);
   var match_X_YMD = X_YMD.exec(input);
   var match_Xst_last_WD = Xst_last_WD.exec(input);
   var isEvery = input.indexOf(EACH) > -1 || input.indexOf(EVERY) > -1 ? true : false;
   if (isEvery === false)
   {
      match_For = null;
      match_Until = null
   }
   var hasMonthLit = input.indexOf(MONT) > -1;
   var hasOf = input.indexOf(OF) > -1;
   
   // is Xst weekday?
   if (match_Xst && match_Xst_WD)
   {
      // has month literal?
      if (hasMonthLit)
      {         
         match_Xst_WD = null;
         // is Xst last weekday?
         if (match_Xst_last_WD)
         {
            // take match_Xst_last_WD
            match_Xst = null
         }
         
         // take match_Xst
      }
      else
      {
         // take match_Xst_WD
         match_Xst_last_WD = null;
         match_Xst = null
      }
   }
   
   // has YMWD and weekday and is not monthly?
   if (match_X_YMD && (match_Xst_WD && !hasMonthLit))
   {
      // take match_Xst_WD
      match_X_YMD = null
   }
   else
   {
      // has YMWD and weekday and is monthly?
      if (match_X_YMD && (match_Xst_WD && hasMonthLit))
      {
         // take match_X_YMD
         match_Xst_WD = null
      }
   }
   
   // has YMWD and Xst weekday and of
   if (match_X_YMD && (match_Xst_last_WD && hasOf))
   {
      // take match_Xst_last_WD
      match_X_YMD = null
   }
   else
   {
      // has YMWD and Xst weekday and not of
      if (match_X_YMD && (match_Xst_last_WD && !hasOf))
      {
         // take match_X_YMD
         match_Xst_last_WD = null
      }
   }
   if (match_Y_Xst_WD_in_M && match_X_YMD)
   {
      match_X_YMD = null
   }
   if (match_Y_Xst_WD_in_M)
   {
      output = [YEARLY_WEEK, match_Y_Xst_WD_in_M, match_Until, match_For, isEvery]
   }
   else
   {
      if (match_X_M_Y)
      {
         output = [YEARLY, match_X_M_Y, match_Until, match_For, isEvery]
      }
      else
      {
         if (match_Xst_last_WD)
         {
            var R = input.split(/\s*(?:and|\,)\s*/);
            var U = [];
            U.push(match_Xst_last_WD);
            if (R.length > 1)
            {
               for (var AB = 1; AB < R.length; AB++)
               {
                  U.push(Xst_last_WD.exec(R[AB]))
               }
            }
            match_Xst_last_WD = U;
            output = [DAY_OF_MONTH, match_Xst_last_WD, match_Until, match_For, isEvery]
         }
         else
         {
            if (match_Xst)
            {
               var R = input.split(/\s*(?:and|\,)\s*/);
               var U = [];
               U.push(match_Xst);
               if (R.length > 1)
               {
                  for (var AB = 1; AB < R.length; AB++)
                  {
                     U.push(Xst.exec(R[AB]))
                  }
               }
               match_Xst = U;
               output = [MONTHLY, match_Xst, match_Until, match_For, isEvery]
            }
            else
            {
               if (match_Xst_WD)
               {
                  var R = input.split(/\s*(?:and|\,)\s*/);
                  var U = [];
                  U.push(match_Xst_WD);
                  if (R.length > 1)
                  {
                     for (var AB = 1; AB < R.length; AB++)
                     {
                        U.push(Xst_WD.exec(R[AB]))
                     }
                  }
                  match_Xst_WD = U;
                  output = [WEEKLY, match_Xst_WD, match_Until, match_For, isEvery]
               }
               else
               {
                  if (match_X_YMD)
                  {
                     var R = input.split(/\s*(?:and|\,)\s*/);
                     var U = [];
                     U.push(match_X_YMD);
                     if (R.length > 1)
                     {
                        for (var AB = 1;
                        AB < R.length; AB++)
                        {
                           U.push(X_YMD.exec(R[AB]))
                        }
                     }
                     match_X_YMD = U;
                     output = [SPEC, match_X_YMD, match_Until, match_For, isEvery]
                  }
               }
            }
         }
      }
   }
   if (output !== null)
   {
      output.push(AD)
   }
   return output
};
vg.prototype.convertRrule = function (AB)
{
   var COMMA = ",",
      SEMICOLON = ";",
      EQUALS = "=",
      OP_BYDAY = "BYDAY",
      OP_BYMONTH = "BYMONTH",
      OP_BYMONTHDAY= "BYMONTHDAY",
      OP_COUNT = "COUNT",
      OP_DAILY = "DAILY",
      FR = "FR",
      OP_FREQ = "FREQ",
      OP_INTERVAL = "INTERVAL",
      MO = "MO",
      OP_MONTHLY = "MONTHLY",
      SA = "SA",
      SU = "SU",
      TH = "TH",
      TU = "TU",
      OP_UNTIL = "UNTIL",
      WE = "WE",
      OP_WEEKLY = "WEEKLY",
      OP_YEARLY = "YEARLY",
      DATE_PATTERN = "YYYYmmDDTHHMMSS",
      C_LIT = "c",
      DAY_OF_MONTH = "day_of_month",
      E_LIT = "e",
      F_LIT = "f",
      H_LIT = "h",
      I_LIT = "i",
      L_LIT = "l",
      MONTHLY = "monthly",
      N_LIT = "n",
      O_LIT = "o",
      R_LIT = "r",
      S_LIT = "s",
      SPEC = "spec",
      T_LIT = "t",
      undef = "undefined",
      V_LIT = "v",
      W_LIT = "w",
      WEEKLY = "weekly",
      YEARLY_WEEK = "yearly_week";
   var j = this.parseReoccurrence(AB);
   if (!j)
   {
      return null
   }
   var recurrence = j[0];
   var parserMatch = j[1];
   var untilTime = j[2] ? j[2][0].formatUTC(DATE_PATTERN) : null;
   var forMatch = j[3];
   var isEvery = j[4];
   var outHash =
   {
   };

   function parseXst(input)
   {
      if (typeof input === undef || input === null)
      {
         return 1
      }
      var E = parseInt(input, 10);
      if (!isNaN(E))
      {
         return E
      }
      input = input.trim().toLowerCase();
      var charAt0 = input.charAt(0);
      var charAt1 = input.charAt(1);
      if (charAt0 === F_LIT)
      {
         if (charAt1 === I_LIT)
         {
            return 1
         }
         else
         {
            if (charAt1 === O_LIT)
            {
               return 4
            }
         }
      }
      else
      {
         if (charAt0 === T_LIT)
         {
            return 3
         }
         else
         {
            if (charAt0 === L_LIT)
            {
               return 4
            }
            else
            {
               if (charAt0 === O_LIT)
               {
                  return 2
               }
               else
               {
                  if (charAt0 === S_LIT)
                  {
                     if (charAt1 === E_LIT)
                     {
                        return 2
                     }
                  }
               }
            }
         }
      }
      return 1
   }
   function findWeekday(input)
   {
      var weekday_array =
      {
         mon: MO,
         tue: TU,
         wed: WE,
         thu: TH,
         fri: FR,
         sat: SA,
         sun: SU
      };
      input = input.trim().toLowerCase();
      for (var B in weekday_array)
      {
         if (input.indexOf(B) > -1)
         {
            return weekday_array[B]
         }
      }
      return null
   }
   function parseWeekly(input)
   {
      var interval = null;
      var o = [];
      for (var k = 0, E = input.length; k < E; k++)
      {
         var singleMatch = input[k],
            F;
            
         // not 1st match and no Xst
         if (k !== 0 && singleMatch[3] === null)
         {
            F = null
         }
         
         // 1st match or not 1st match with Xst
         else
         { 
            F = singleMatch[3] === null ? 1 : singleMatch[3];
            F = parseXst(F);
            
            // only take inteval from 1st match
            if (k === 0)
            {
               interval = F
            }
         }
         var A = singleMatch[4];
         if (A === null)
         {
            continue
         }
         A = findWeekday(A);
         o.push(A)
      }
      outHash[OP_FREQ] = OP_WEEKLY;
      outHash[OP_INTERVAL] = interval;
      outHash[OP_BYDAY] = o.join(COMMA)
   }
   function parseMonthly(input)
   {
      var A = [];
      map(function (D)
      {
         var E = parseXst(D);
         A.push(E)
      }, input);
      outHash[OP_FREQ] = OP_MONTHLY;
      outHash[OP_INTERVAL] = 1;
      outHash[OP_BYMONTHDAY] = A.join(COMMA)
   }
   function parseNumber(input)
   {
      if (typeof input === undef || input === null)
      {
         return 1
      }
      var F = parseInt(input, 10);
      if (!isNaN(F))
      {
         return F
      }
      input = input.trim().toLowerCase();
      var charAt0 = input.charAt(0);
      var charAt1 = input.charAt(1);
      var charAt2 = input.charAt(2);
      if (charAt0 === F_LIT)
      {
         if (charAt1 === I_LIT)
         {
            if (charAt2 === R_LIT)
            {
               return 1
            }
            else
            {
               if (charAt2 === V_LIT)
               {
                  return 5
               }
            }
         }
         else
         {
            if (charAt1 === O_LIT)
            {
               return 4
            }
         }
      }
      else
      {
         if (charAt0 === T_LIT)
         {
            if (charAt1 === H_LIT)
            {
               return 3
            }
            else
            {
               if (charAt1 === W_LIT)
               {
                  return 2
               }
               else
               {
                  if (charAt1 === E_LIT)
                  {
                     return 10
                  }
               }
            }
         }
         else
         {
            if (charAt0 === L_LIT)
            {
               return 4
            }
            else
            {
               if (charAt0 === O_LIT)
               {
                  if (charAt1 === T_LIT)
                  {
                     return 2
                  }
                  else
                  {
                     if (charAt1 === N_LIT)
                     {
                        return 1
                     }
                  }
               }
               else
               {
                  if (charAt0 === S_LIT)
                  {
                     if (charAt1 === I_LIT)
                     {
                        return 6
                     }
                     else
                     {
                        if (charAt1 === E_LIT)
                        {
                           if (charAt2 === C_LIT)
                           {
                              return 2
                           }
                           else
                           {
                              if (charAt2 === V_LIT)
                              {
                                 return 7
                              }
                           }
                        }
                     }
                  }
                  else
                  {
                     if (charAt0 === E_LIT)
                     {
                        return 8
                     }
                     else
                     {
                        if (charAt0 === N_LIT)
                        {
                           return 9
                        }
                     }
                  }
               }
            }
         }
      }
      return 1
   }
   function parseSpec(input)
   {
      input = input[0];
      var A = input[1];
      var D = input[3] === null ? 1 : input[3];
      var k = input[4].trim().toLowerCase();
      var F = k.charAt(0);
      var E =
      {
         d: OP_DAILY,
         w: OP_WEEKLY,
         m: OP_MONTHLY,
         y: YEARLY
      };
      if (F in E)
      {
         k = E[F]
      }
      outHash[OP_FREQ] = k;
      outHash[OP_INTERVAL] = parseNumber(D)
   }
   function parseXstReverse(input)
   {
      if (typeof input === undef || input === null)
      {
         return 1
      }
      var E = parseInt(input, 10);
      if (!isNaN(E))
      {
         return E
      }
      input = input.trim().toLowerCase();
      var charAt0 = input.charAt(0);
      var charAt1 = input.charAt(1);
      if (charAt0 === F_LIT)
      {
         if (charAt1 === I_LIT)
         {
            return 1
         }
         else
         {
            if (charAt1 === O_LIT)
            {
               return 4
            }
         }
      }
      else
      {
         if (charAt0 === T_LIT)
         {
            return 3
         }
         else
         {
            if (charAt0 === L_LIT)
            {
               return -1
            }
            else
            {
               if (charAt0 === O_LIT)
               {
                  return 2
               }
               else
               {
                  if (charAt0 === S_LIT)
                  {
                     if (charAt1 === E_LIT)
                     {
                        return 2
                     }
                  }
               }
            }
         }
      }
      return 1
   }
   function parseDay_Of_Month(input)
   {
      var m = null;
      var o = [];
      for (var k = 0, E = input.length; k < E; k++)
      {
         var singleMatch = input[k],
            F;
         if (k !== 0 && singleMatch[2] === null)
         {
            F = null
         }
         else
         {
            F = singleMatch[2] === null ? 1 : singleMatch[2];
            F = parseXstReverse(F);
            if (k === 0)
            {
               m = F
            }
         }
         var A = singleMatch[4];
         if (A === null)
         {
            continue
         }
         A = findWeekday(A);
         
         // is last set?
         if (singleMatch[3] !== null)
         {
            A = m * -1 + A
         }
         else
         {
            A = m + A
         }
         o.push(A)
      }
      outHash[OP_FREQ] = OP_MONTHLY;
      outHash[OP_INTERVAL] = 1;
      outHash[OP_BYDAY] = o.join(COMMA)
   }
   var MONTH_ARRAY =
   {
      JAN: 1,
      FEB: 2,
      MAR: 3,
      APR: 4,
      MAY: 5,
      JUN: 6,
      JUL: 7,
      AUG: 8,
      SEP: 9,
      OCT: 10,
      NOV: 11,
      DEC: 12
   };

   function parseYearlyWeek(input)
   {
      var A, k;
      var E = parseXstReverse(input[2]);
      var F = findWeekday(input[3]);
      A = E + F;
      var D = input[4].substring(0, 3).toUpperCase();
      k = D in MONTH_ARRAY ? MONTH_ARRAY[D] * 1 : 1;
      outHash[OP_FREQ] = OP_YEARLY;
      outHash[OP_INTERVAL] = 1;
      outHash[OP_BYDAY] = A;
      outHash[OP_BYMONTH] = k
      
      switch (recurrence)
      {
      case SPEC:
         parseSpec(parserMatch);
         break;
      case WEEKLY:
         parseWeekly(parserMatch);
         break;
      case MONTHLY:
         parseMonthly(parserMatch);
         break;
      case DAY_OF_MONTH:
         parseDay_Of_Month(parserMatch);
         break;
      case YEARLY_WEEK:
         parseYearlyWeek(parserMatch);
         break
      }
      if (untilTime)
      {
         outHash[OP_UNTIL] = untilTime
      }
      if (forMatch)
      {
         outHash[OP_COUNT] = forMatch
      }
      var AM = [];
      for (var AK in outHash)
      {
         AM.push(AK + EQUALS + outHash[AK])
      }
      var I = AM.join(SEMICOLON);
      if (recurrence === SPEC || (recurrence === WEEKLY || (recurrence === MONTHLY || (recurrence === DAY_OF_MONTH || recurrence === YEARLY_WEEK))))
      {
         return [I, isEvery ? 1 : 0]
      }
      return null
   }
};