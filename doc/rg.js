function rg()
{
   this.timeEstimateCache =
   {
   };
   this.timeEstimateFormatCache =
   {
   };
   this.timeSpecCache =
   {
   };
   this.serverDate = null;
   this.startDate = null;
   this.serverOffset = 0;
   this.chimeTimeout = null;
   this.monthLength = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
   this.numbers =
   {
      zero: 0,
      one: 1,
      two: 2,
      three: 3,
      four: 4,
      five: 5,
      six: 6,
      seven: 7,
      eight: 8,
      nine: 9
   };
   this.cache =
   {
   };
   this.rangeCache =
   {
   };
   this.useCache = false;
   this.formatCache =
   {
   };
   this.friendlyCache =
   {
   };
   this.friendlyTimeCache =
   {
   };
   this.timezoneOffset = null;
   this.noMaint = false
}
rg.INSTANCE = null;
rg.getInstance = function ()
{
   if (rg.INSTANCE === null)
   {
      rg.INSTANCE = new rg()
   }
   return rg.INSTANCE
};
rg.prototype.getCachedIntlFormat = function (B, A)
{
   if (!is(this.formatCache[B]))
   {
      this.formatCache[B] =
      {
      }
   }
   if (this.formatCache[B][A])
   {
      return this.formatCache[B][A]
   }
   return this.formatCache[B][A] = DateFormat(B, new Date(A * 1000))
};
rg.friendlyDateMap =
{
   es: "d/MM/yy",
   fr: "dd/MM/yy",
   ja: "yy'\u5E74'M'\u6708'd'\u65E5'",
   nl: "d-MM-yy",
   "pt-BR": "dd/MM/yy",
   "pt-PT": "dd/MM/yy",
   "zh-CN": "yy'\u5E74'M'\u6708'd'\u65E5'",
   "zh-TW": "yy'\u5E74'M'\u6708'd'\u65E5'",
   tr: "dd.MM.yy",
   bs: "yy-MM-dd",
   sv: "yy-MM-dd",
   de: "dd.MM.yy",
   pl: "yy-MM-dd",
   it: "dd/MM/yy",
   ru: "dd.MM.yy",
   cs: "d.M.yy",
   ko: "yy-MM-dd",
   no: "dd.MM.yy",
   hu: "yy.MM.dd.",
   lv: "yy.d.M",
   lt: "yy.MM.dd",
   da: "dd/M/yy",
   eo: "yy-MM-dd",
   ca: "dd/MM/yy"
};
rg.prototype.getAutocompletions = function ()
{
   var J = "EEEE",
      O = "INTERFACE_OVERVIEW_TODAY",
      M = "INTERFACE_OVERVIEW_TOMORROW",
      K = "day";
   var I = new Date();
   var A = null;
   var F = [];
   if (RTM.isEnglish())
   {
      F.push(_T(O).toLowerCase());
      F.push(_T(M).toLowerCase())
   }
   else
   {
      F.push(_T(O));
      F.push(_T(M))
   }
   for (var E = 0; E < 5; E++)
   {
      var D = new Date(I);
      this.calculateUnit(D, E + 2, K, false);
      var B = D.getTime();
      var H = rg.getCachedIntlFormatForDate(J, D, B);
      if (RTM.isEnglish())
      {
         F.push(H.toLowerCase())
      }
      else
      {
         F.push(H)
      }
   }
   return F
};
rg.prototype.getFriendlyDate = function (B, E)
{
   var K = "EEEE",
      Q = "HH:mm",
      M = "INTERFACE_OVERVIEW_TODAY",
      I = "MM/dd/yy",
      J = "MMM dd",
      H = "dd/MM/yy",
      R = "en-GB",
      U = "en-US",
      O = "h:mmz";
   var A = parseInt(B, 10);
   if (!E && A in this.friendlyCache)
   {
      return this.friendlyCache[A]
   }
   else
   {
      if (E && A in this.friendlyTimeCache)
      {
         return this.friendlyTimeCache[A]
      }
   }
   var V = A * 1000;
   var F = new Date(V);
   var D = null;
   if (V >= rg.PRE_TODAY && V < rg.PRE_TOMORROW)
   {
      if (E)
      {
         if (!(rtmLanguage === U || rtmLanguage === R))
         {
            D = rg.getCachedIntlFormatForDate(Intl.preferred_formats.hmm_a, F, V)
         }
         else
         {
            D = rg.getCachedIntlFormatForDate(rtmTime24 ? Q : O, F, V)
         }
      }
      else
      {
         D = _T(M)
      }
   }
   else
   {
      if (V >= rg.PRE_TOMORROW && V < rg.PRE_WEEK)
      {
         D = rg.getCachedIntlFormatForDate(K, F, V)
      }
      else
      {
         if (V >= rg.PRE_THIS_YEAR && V < rg.PRE_SIX_MONTHS)
         {
            if (!(rtmLanguage === U || rtmLanguage === R))
            {
               D = rg.getCachedIntlFormatForDate(Intl.preferred_formats.list_date, F, V)
            }
            else
            {
               D = rg.getCachedIntlFormatForDate(J, F, V)
            }
         }
         else
         {
            if (!(rtmLanguage === U || rtmLanguage === R) && rtmLanguage in rg.friendlyDateMap)
            {
               D = rg.getCachedIntlFormatForDate(rg.friendlyDateMap[rtmLanguage], F, V)
            }
            else
            {
               if (configurationMgr.configuration.dateFormat == 1)
               {
                  D = rg.getCachedIntlFormatForDate(I, F, V)
               }
               else
               {
                  D = rg.getCachedIntlFormatForDate(H, F, V)
               }
            }
         }
      }
   }
   if (!E)
   {
      this.friendlyCache[A] = D
   }
   else
   {
      this.friendlyTimeCache[A] = D
   }
   return D
};
rg.formatCache =
{
};
rg.getCachedIntlFormatForDate = function (A, D, B)
{
   B = B || D;
   if (!(A in this.formatCache))
   {
      this.formatCache[A] =
      {
      }
   }
   if (this.formatCache[A][B])
   {
      return this.formatCache[A][B]
   }
   return this.formatCache[A][B] = DateFormat(A, D)
};
rg.prototype.setCache = function (A)
{
   this.useCache = !! A;
   this.cache =
   {
   };
   this.rangeCache =
   {
   };
   this.friendlyCache =
   {
   };
   this.friendlyTimeCache =
   {
   }
};
rg.prototype.setTimezoneOffset = function (A)
{
   this.timezoneOffset = A
};
rg.prototype.getDay = function (B)
{
   var A = Date.Days;
   var D =
   {
   };
   for (var E = 0; E < A.length; E++)
   {
      D[A[E].substring(0, 3).toLowerCase()] = E
   }
   B = B.length > 3 ? B.substring(0, 3).toLowerCase() : B.toLowerCase();
   return D[B]
};
rg.prototype.calculateDate = function (D, E, A)
{
   var B = 0;
   if (E >= D)
   {
      D = D + 7
   }
   if (A)
   {
      B = 7 + (D - E)
   }
   else
   {
      B = D - E
   }
   return B
};
rg.prototype.checkDate = function (K, H)
{
   var B = K;
   var A = parseInt(B.getFullYear(), 10);
   var E = parseInt(B.getMonth(), 10);
   if (H)
   {
      var I = parseInt(B.getDate(), 10);
      I += H
   }
   else
   {
      var I = parseInt(B.getDate(), 10)
   }
   if (!is(I) || (!is(E) || !is(A)))
   {
      return false
   }
   var D, F;
   do
   {
      if (E == 12)
      {
         A = parseInt(A, 10) + 1;
         E = 0
      }
      D = Date.days_in_month(A, E + 1);
      if (!(I > D))
      {
         break
      }
      F = parseInt(I, 10) - D;
      E = parseInt(E, 10) + 1;
      I = F
   } while (I > D);
   var J = new Date(B);
   J.setYear(A);
   J.setMonth(E);
   J.setDate(I);
   J.setHours(B.getHours());
   J.setMinutes(B.getMinutes());
   J.setSeconds(B.getSeconds());
   return J
};
rg.prototype.calculateUnit = function (K, H, B, E)
{
   var Y = "d",
      W = "m",
      U = "undefined",
      X = "w",
      V = "y";
   var Q = 0;
   var O = 0;
   var A = 0;
   var I = K.getFullYear();
   var J = K.getMonth();
   var M = K.getDate();
   var D = B.toLowerCase().charAt(0);
   var F = parseInt(H, 10);
   if (isNaN(F))
   {
      F = this.numbers[H.toLowerCase()]
   }
   switch (D)
   {
   case Y:
      Q = F;
      break;
   case X:
      Q = F * 7;
      break;
   case W:
      O = F;
      break;
   case V:
      A = F;
      break
   }
   if (!E)
   {
      I += A;
      J += O;
      M += Q
   }
   else
   {
      I -= A;
      J -= O;
      M -= Q
   }
   K.setYear(I);
   K.setMonth(J);
   var R;
   if (typeof is_safari !== U && (is_safari && !is_safari_3))
   {
      R = this.checkDate(K, Q)
   }
   else
   {
      K.setDate(M);
      R = this.checkDate(K)
   }
   return R
};
rg.prototype.getMonth = function (A)
{
   var E = Date.Months;
   var D =
   {
   };
   for (var B = 0; B < E.length; B++)
   {
      D[E[B].substring(0, 3).toLowerCase()] = B + 1
   }
   A = A.length > 3 ? A.substring(0, 3).toLowerCase() : A.toLowerCase();
   return D[A]
};
rg.prototype.getYear = function (D)
{
   var A = "20",
      B = "200";
   return D.length == 1 ? parseInt(B + D, 10) : D.length == 2 ? parseInt(A + D, 10) : D
};
rg.prototype.getEpochForDate = function (A)
{
   var B = this.parseDueDate(A);
   if (B && B[0])
   {
      return parseInt(B[0].getTime(), 10)
   }
   else
   {
      return null
   }
};
rg.prototype.hideMaint = function ()
{
   this.noMaint = rtmMaintTS;
   this.updateDateTime(this, new Date())
};
rg.prototype.updateDateTime = function (V, W)
{
   var F = '</a> [<a href=" " onclick="dateTimeMgr.hideMaint(); return false;">x</a>]</b>',
      H = '<b><a style="color: #EA5200;" href="http://status.rememberthemilk.com/" target="_blank">Planned maintenance starting on ',
      Q = "SmartAdd",
      U = "datetime",
      K = "in 1 week",
      J = "in 7 months",
      R = "today",
      M = "tomorrow",
      E = "undefined",
      O = "yyyy'-01-01'",
      I = "yyyy'-MM-01'";
   var D = el(U);
   if (D && W)
   {
      D.innerHTML = DateFormat(Intl.preferred_formats.current_date);
      var B = W.getHours();
      var X = W.getMinutes();
      if (B === 0 && X === 0)
      {
         rg.PRE_TODAY = this.getEpochForDate(R);
         rg.PRE_THIS_YEAR = this.getEpochForDate(DateFormat(O, new Date(rg.PRE_TODAY)));
         rg.PRE_TOMORROW = this.getEpochForDate(M);
         rg.PRE_WEEK = this.getEpochForDate(K);
         rg.PRE_SIX_MONTHS = this.getEpochForDate(J);
         rg.PRE_SIX_MONTHS = this.getEpochForDate(DateFormat(I, new Date(rg.PRE_SIX_MONTHS)));
         this.friendlyCache =
         {
         };
         this.friendlyTimeCache =
         {
         };
         taskList.updateForNewDay()
      }
      if (this.noMaint !== rtmMaintTS && (rtmMaintTS && (W.getTime() < rtmMaintTS && X % 2 === 0)))
      {
         var A = new Date(rtmMaintTS);
         D.innerHTML = H + DateFormat(Intl.preferred_formats.due_time, A) + F
      }
      if (typeof globalScope !== E && Q in globalScope)
      {
         wg.AUTO_DUE = rg.getInstance().getAutocompletions()
      }
   }
};
rg.prototype.init = function ()
{
   var D = "chime";
   this.mbn = this.getUniqueMessageBusName();
   var B = this;
   var A = function (F, E)
   {
      B.updateDateTime(F, E)
   };
   messageBus.subscribe(A, this.mbn + D);
   this.startChime()
};
rg.PRE_TODAY = null;
rg.PRE_TOMORROW = null;
rg.PRE_WEEK = null;
rg.PRE_SIX_MONTHS = null;
rg.PRE_THIS_YEAR = null;
rg.prototype.getUniqueMessageBusName = function ()
{
   var A = "rtm.datetimemanager.";
   return A
};
rg.prototype.startChime = function ()
{
   var F = "in 1 week",
      E = "in 7 months",
      B = "today",
      H = "tomorrow",
      D = "yyyy'-'MM'-01'",
      A = "yyyy'-01-01'";
   rg.PRE_TODAY = this.getEpochForDate(B);
   rg.PRE_THIS_YEAR = this.getEpochForDate(DateFormat(A, new Date(rg.PRE_TODAY)));
   rg.PRE_TOMORROW = this.getEpochForDate(H);
   rg.PRE_WEEK = this.getEpochForDate(F);
   rg.PRE_SIX_MONTHS = this.getEpochForDate(E);
   rg.PRE_SIX_MONTHS = this.getEpochForDate(DateFormat(D, new Date(rg.PRE_SIX_MONTHS)));
   this.chime()
};
rg.prototype.chime = function ()
{
   var B = "chime";
   var D = this;
   var A = function ()
   {
      D.chime()
   };
   this.chimeTimeout = setTimeout(A, 1000 * 60);
   messageBus.broadcast(this, this.mbn + B, new Date())
};
rg.prototype.stopChime = function ()
{
   clearTimeout(this.chimeTimeout);
   this.chimeTimeout = null
};
rg.prototype.setServerDate = function (A)
{
   if (A < 2000000000)
   {
      A *= 1000
   }
   if (this.startDate === null)
   {
      this.startDate = new Date(A)
   }
   this.serverDate = new Date(A);
   var B = new Date();
   this.serverOffset = B.getTime() - this.serverDate.getTime()
};
rg.prototype.setTimezone = function (E)
{
   var A = "/",
      B = "TZ",
      F = "auth.setTimezone";
   var D = new Date();
   setCookie(B, D.getTimezoneOffset(), null, A, null, null);
   if (!E)
   {
      if (rtmIsOffline)
      {
         return
      }
      transMgr.request(F, utility.encodeJavaScript(
      {
         tz: D.getTimezoneOffset()
      }))
   }
};
rg.prototype.parseTimeEstimate = function (I, H)
{
   var O = ".",
      M = "0",
      R = "en-GB",
      U = "en-US",
      Q = "undefined";
   if (!H && (rtmLanguage !== U && rtmLanguage !== R))
   {
      if (this.timeEstimateCache[I])
      {
         return this.timeEstimateCache[I]
      }
      var A = null;
      if ((A = this.parseTimeEstimate(I, true)) !== null)
      {
         return A
      }
      var K = this.parseDueDate(I);
      if (K !== null)
      {
         I = K[5]
      }
   }
   var J, F, B;
   var V = false;
   if (this.timeEstimateCache[I])
   {
      return this.timeEstimateCache[I]
   }
   J = F = B = 0;
   var D = /([0-9.]+)\s*(days|day|d)/i.exec(I);
   if (D != null)
   {
      if (typeof D[0] != Q)
      {
         J += parseInt(D[1], 10);
         V = true
      }
   }
   var D = /([0-9.]+)\s*(hours|hour|hrs|hr|h)/i.exec(I);
   if (D != null)
   {
      if (typeof D[0] != Q)
      {
         var E = D[1].indexOf(O);
         if (E > -1)
         {
            if (E > 0)
            {
               F = parseInt(D[1].substring(0, E), 10)
            }
            else
            {
               F = 0
            }
            B = parseFloat(M + D[1].substring(E, D[1].length)) * 60
         }
         else
         {
            F = parseInt(D[1], 10)
         }
         V = true
      }
   }
   var D = /([0-9.]+)\s*(minutes|minute|mins|min|m)/i.exec(I);
   if (D != null)
   {
      if (typeof D[0] != Q)
      {
         B += parseInt(D[1], 10);
         V = true
      }
   }
   if (V === false)
   {
      return null
   }
   B = J * 24 * 60 + F * 60 + B;
   this.timeEstimateCache[I] = B;
   return B
};
rg.prototype.formatTimeEstimate = function (B)
{
   var H = ", ",
      O = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_DAYS",
      K = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_HOURS",
      I = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_MINUTES",
      Q = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_DAY",
      M = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_HOUR",
      J = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_MINUTE";
   if (this.timeEstimateFormatCache[B])
   {
      return this.timeEstimateFormatCache[B]
   }
   var F, D, A;
   F = D = A = 0;
   A = B;
   F = Math.floor(A / (24 * 60));
   A = Math.floor(A % (24 * 60));
   D = Math.floor(A / 60);
   A = Math.floor(A % 60);
   var E = [];
   if (F === 1)
   {
      E.push(_T(Q))
   }
   else
   {
      if (F > 1)
      {
         E.push(_T(O, {
            NUM: F
         }))
      }
   }
   if (D === 1)
   {
      E.push(_T(M))
   }
   else
   {
      if (D > 1)
      {
         E.push(_T(K, {
            NUM: D
         }))
      }
   }
   if (A === 1)
   {
      E.push(_T(J))
   }
   else
   {
      if (A > 1)
      {
         E.push(_T(I, {
            NUM: A
         }))
      }
   }
   E = E.join(H);
   this.timeEstimateFormatCache[B] = E;
   return E
};
rg.prototype.parseTimeSpec = function (B, V)
{
   var Q = ".",
      O = "0",
      U = "_",
      R = "undefined";
   var A = false;
   var K, F, D;
   var V = !! V;
   var M = null;
   var I = [B, V].join(U);
   if (this.timeSpecCache[I])
   {
      return this.timeSpecCache[I]
   }
   K = F = D = 0;
   var H = /([0-9]+)\:([0-9]+)\:([0-9]+)/i.exec(B);
   if (H != null)
   {
      if (typeof H[0] != R)
      {
         K = parseInt(H[1], 10);
         F = parseInt(H[2], 10);
         D = parseInt(H[3], 10);
         A = true;
         M = B.indexOf(H[0])
      }
   }
   else
   {
      var H = /([0-9.]+)\s*(hours|hour|hrs|hr|h)/i.exec(B);
      if (H != null)
      {
         if (typeof H[0] != R)
         {
            var J = H[1].indexOf(Q);
            if (J > -1)
            {
               K = parseInt(H[1].substring(0, J), 10);
               F = parseFloat(O + H[1].substring(J, H[1].length)) * 60
            }
            else
            {
               K = parseInt(H[1], 10)
            }
            A = true;
            M = B.indexOf(H[0])
         }
      }
      var H = /([0-9.]+)\s*(minutes|minute|mins|min|m)/i.exec(B);
      if (H != null)
      {
         if (typeof H[0] != R)
         {
            F += parseInt(H[1], 10);
            A = true;
            if (M === null)
            {
               M = B.indexOf(H[0])
            }
         }
      }
      var H = /([0-9.]+)\s*(seconds|second|secs|sec|s)/i.exec(B);
      if (H != null)
      {
         if (typeof H[0] != R)
         {
            D += parseInt(H[1], 10);
            A = true;
            if (M === null)
            {
               M = B.indexOf(H[0])
            }
         }
      }
   }
   D = K * 60 * 60 + F * 60 + D;
   if (A)
   {
      var E = V ? [D, M] : D;
      this.timeSpecCache[I] = E;
      return E
   }
   else
   {
      return null
   }
};
rg.prototype.splitTimeSpec = function (A)
{
   var B, E, D;
   B = E = D = 0;
   D = A;
   B = Math.floor(D / (60 * 60));
   E = Math.floor(D % (60 * 60));
   D = Math.floor(E % 60);
   E = Math.floor(E / 60);
   return [B, E, D]
};
rg.prototype.formatTimeSpec = function (E)
{
   var Q = "",
      J = " and ",
      M = ", ",
      O = "hour",
      K = "minute",
      I = "second";
   var B = this.splitTimeSpec(E);
   var F = B[0];
   var D = B[1];
   var A = B[2];
   var H = Q;
   if (F > 0)
   {
      H += utility.pluralize(F, O)
   }
   if (D > 0)
   {
      if (F > 0)
      {
         H += M + utility.pluralize(D, K)
      }
      else
      {
         H += utility.pluralize(D, K)
      }
   }
   if (A > 0)
   {
      if (F > 0 || D > 0)
      {
         H += J + utility.pluralize(A, I)
      }
      else
      {
         H += utility.pluralize(A, I)
      }
   }
   return H
};
rg.prototype.convertTimeSpec = function (D)
{
   var B = ":";
   var A = this.parseTimeSpec(D);
   if (A == null)
   {
      return null
   }
   A = this.splitTimeSpec(A);
   for (var E = 0; E < A.length; E++)
   {
      A[E] = padded(A[E])
   }
   return A.join(B)
};
rg.prototype.normalizeAMPM = function (D, B)
{
   var I = "a",
      O = "p",
      H = "\u4E0A",
      M = "\u4E0B",
      F = "\u5348\u524D",
      K = "\u5348\u5F8C",
      E = "\uC624\uC804",
      J = "\uC624\uD6C4";
   if (B)
   {
      var A = B.toLowerCase().charAt(0);
      D = parseInt(D, 10);
      if (D != 12 && (A == O || (A == M || (B == K || B == J))))
      {
         D = D + 12
      }
      D = parseInt(D, 10);
      if (D == 12)
      {
         if (A == I || (A == H || (B == F || B == E)))
         {
            D = 0
         }
         else
         {
            D = 12
         }
      }
   }
   else
   {
      D = parseInt(D, 10)
   }
   return D
};
rg.prototype.formatTime = function (B, D)
{
   var H = "",
      Q = "00:00",
      O = "12am",
      M = ":",
      K = ":00",
      J = "am",
      I = "pm";
   if (B[0] == null)
   {
      if (D)
      {
         return Q
      }
      return O
   }
   if (D)
   {
      return padded(B[0]) + M + padded(B[1]) + K
   }
   var A = J;
   if (B[0] > 11)
   {
      A = I
   }
   var F = B[0] || 12;
   if (F > 12)
   {
      F = F - 12
   }
   var E = B[1] != 0 ? M + padded(B[1]) : H;
   return F + E + A
};
rg.prototype.parseTime = function (B)
{
   var M = "12pm",
      R = "never",
      O = "today 12pm",
      Q = "tomorrow 12am";
   if (B.trim().length == 0 || B.trim().indexOf(R) > -1)
   {
      return [null, null]
   }
   B = B.replace(/midnight/i, Q);
   B = B.replace(/midday/i, O);
   B = B.replace(/noon/i, M);
   var F = /(@|at)?\s*([0-9]+)(?::|\.)([0-9]+)\s*(am|pm)?/i;
   var H = /(@|at)?\s*([0-9]{3,4})\s*(am|pm)?/i;
   var A = /(@|at)?\s*([0-9]{1,2})\s*(am|pm)?/i;
   var K = F.exec(B);
   var J = H.exec(B);
   var I = A.exec(B);
   if (!K && (!J && !I))
   {
      return [null, null]
   }
   var E = null,
      D = null;
   if (K)
   {
      if (K[2])
      {
         E = K[2];
         if (K[3])
         {
            D = parseInt(K[3], 10)
         }
         else
         {
            D = 0
         }
         E = this.normalizeAMPM(E, K[4])
      }
   }
   if (!K && J)
   {
      if (J[2])
      {
         if (J[2].length == 3)
         {
            E = parseInt(J[2].substring(0, 1), 10);
            D = parseInt(J[2].substring(1, 3), 10)
         }
         else
         {
            E = parseInt(J[2].substring(0, 2), 10);
            D = parseInt(J[2].substring(2, 4), 10)
         }
         E = this.normalizeAMPM(E, J[3])
      }
   }
   if (!K && (!J && I))
   {
      if (I[2])
      {
         E = parseInt(I[2], 10);
         D = 0;
         E = this.normalizeAMPM(E, I[3])
      }
   }
   return [E, D]
};
rg.prototype.parseDueRange = function (F, J)
{
   var Q = "1",
      R = "of",
      U = "today";
   var H = F;
   F = F.trim();
   if (this.useCache && this.rangeCache[H])
   {
      return this.rangeCache[H]
   }
   if (F.length == 0)
   {
      return this.useCache ? (this.rangeCache[H] = null) : null
   }
   var E = F.split(/\bof\b/i);
   var I, O;
   if (E.length == 1)
   {
      I = E[0];
      O = U
   }
   else
   {
      if (E.length == 2)
      {
         I = E[0];
         O = E[1]
      }
      else
      {
         I = E.shift();
         O = E.join(R)
      }
   }
   I = I.trim();
   O = O.trim();
   var D = this.parseDueDate(O);
   if (D[0] === null)
   {
      return this.useCache ? (this.rangeCache[H] = null) : null
   }
   D = new Date(D[0]);
   I = I.replace(/a /, Q).split(/\s+/);
   if (I.length == 1)
   {
      return this.useCache ? (this.rangeCache[H] = null) : null
   }
   var K = I.shift();
   var A = I.shift();
   var M = this.calculateUnit(new Date(D), K, A, !! J);
   if (!D || !M)
   {
      return this.useCache ? (this.rangeCache[H] = null) : null
   }
   if ( !! matchUpcoming === true)
   {
      var B = D;
      D = M;
      M = B;
      D.setDate(D.getDate() + 1);
      M.setDate(M.getDate() + 1)
   }
   return this.useCache ? (this.rangeCache[H] = new DateRange(D, M)) : new DateRange(D, M)
};
rg.reNow = /\bnow\b/i;
rg.tom = /(today|tod|tomorrow|tom|tonight|ton|tmr|yesterday)/i;
if (typeof is_safari == "undefined" || !is_safari)
{
   rg.fullDate = /([0-9]{1,4})(?:\-|\/|\.|\u5e74|\u6708|\u65e5)([0-9]{1,2})(?:\-|\/|\.|\u5e74|\u6708|\u65e5)*([0-9]{1,4})*/i
}
else
{
   rg.fullDate = new RegExp("([0-9]{1,4})(?:\\-|\\/|\\.|\u5E74|\u6708|\u65E5)([0-9]{1,2})(?:\\-|\\/|\\.|\u5E74|\u6708|\u65E5)*([0-9]{1,4})*", "i")
}

rg.on_Xst_of_M_Y = /(on)?\s*(([0-9]*)(?:st|th|rd|nd)*(?:\s|of|\-a|\-|,|\.)*(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sept|sep|october|oct|november|nov|december|dec)(?:\s|\-|\.)*([0-9]*))/i;
rg.on_M_Xst_Y = /(on)?\s*((january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sept|sep|october|oct|november|nov|december|dec)(?:\s|,|\.|\-)*([0-9]+)(?:st|th|rd|nd)*(?:\s|,|\.|\-a|\-)*([0-9]*))/i;
rg.on_next_weekday = /(on)?\s*((next)?\s*(monday|mon|tuesday|tue|wednesday|wed|thursday|thu|friday|fri|saturday|sat|sunday|sun))/i;
rg.in_X_YMWD = /(in)?\s*(one|two|three|four|five|six|seven|eight|nine|ten|[0-9]+)\s*(years|year|yrs|yr|months|month|mons|mon|weeks|week|wks|wk|days|day)/i;
rg.end_of_WM = /end\s*of\s*(?:the)*\s*(week|w|month|m)/i;
rg.on_Xst = /(on)?\s*([0-9]+)(?:st|th|rd|nd)/i;
if (typeof is_safari == "undefined" || !is_safari)
{
   rg.time_sep = /(@|at|,)?\s*([0-9]+)(?::|\.|\u0020\u0068\u0020|\u6642|h)([0-9]+)(?:\u5206)?\s*(am|a|pm|p|\u4e0a|\u4e0b|\u5348\u524d|\u5348\u5f8c|\uc624\uc804|\uc624\ud6c4)?/i;
   rg.time_hhmm = /(@|at)?\s*([0-9]{3,4})\s*(a|p|\u4e0a|\u4e0b|\u5348\u524d|\u5348\u5f8c|\uc624\uc804|\uc624\ud6c4)?/i;
   rg.time_hh = /(@|at)?\s*([0-9]{1,2})\s*(a|p|\u4e0a|\u4e0b|\u5348\u524d|\u5348\u5f8c|\uc624\uc804|\uc624\ud6c4)?/i
}
else
{
   rg.time_sep = new RegExp("(@|at|,)?\\s*([0-9]+)(?::|.| h |\u6642|h)([0-9]+)(?:\u5206)?\\s*(am|a|pm|p|\u4E0A|\u4E0B|\u5348\u524D|\u5348\u5F8C|\uC624\uC804|\uC624\uD6C4)?", "i");
   rg.time_hhmm = new RegExp("(@|at)?\\s*([0-9]{3,4})\\s*(a|p|\u4E0A|\u4E0B|\u5348\u524D|\u5348\u5F8C|\uC624\uC804|\uC624\uD6C4)?", "i");
   rg.time_hh = new RegExp("(@|at)?\\s*([0-9]{1,2})\\s*(a|p|\u4E0A|\u4E0B|\u5348\u524D|\u5348\u5F8C|\uC624\uC804|\uC624\uD6C4)?", "i")
}
rg.rtmformat = new RegExp("_RTM_:(year=(\\d+))?(?:,)?(month=(\\d+))?(?:,)?(date=(\\d+))?");
rg._r_a = / a /i;
rg._r_midnight = /midnight/i;
rg._r_midday = /midday/i;
rg._r_noon = /noon/i;
rg.prototype.parseDueDate = function (input)
{
   var Ai = " one ",
      AN = ",a",
      AM = ",b",
      AL = ",c",
      AK = ",timespec",
      Aa = "1",
      AJ = "12pm",
      AY = "day",
      Aj = "never",
      Ay = "o",
      Aw = "p",
      Ah = "q",
      AI = "r",
      AQ = "sun",
      Ad = "tmr",
      AW = "tod",
      Af = "tom",
      AO = "tomorrow 12am",
      AV = "ton",
      AH = "v",
      AR = "w",
      AG = "x",
      AT = "yes",
      Az = "z";
   var Q = input;
   var AS = null;
   if (this.useCache && this.cache[Q])
   {
      return this.cache[Q]
   }
   if (input.trim().length == 0 || input.trim().indexOf(Aj) > -1)
   {
      return this.useCache ? (this.cache[Q] = [null, null]) : [null, null]
   }
   var I = new Date();
   input = input.replace(rg._r_a, Ai);
   input = input.replace(rg._r_midnight, AO);
   input = input.replace(rg._r_midday, AJ);
   input = input.replace(rg._r_noon, AJ);
   var K = null,
      A5 = null;
   var K, matchRTMformat, matchNow, matchTom, matchFullDate, matchOn_Xst_of_M_Y, matchOn_next_weekday, matchIn_X_YMWD, BE, matchOn_M_Xst_Y, matchEnd_of_WM;
   K = matchRTMformat = matchNow = matchTom = matchFullDate = matchOn_Xst_of_M_Y = matchOn_next_weekday = matchIn_X_YMWD = matchOn_Xst = matchOn_M_Xst_Y = matchEnd_of_WM = null;
   matchRTMformat = rg.rtmformat.exec(input);
   
   if (!(matchRTMformat && matchRTMformat[0]))
   {
      matchOn_M_Xst_Y = rg.on_M_Xst_Y.exec(input);
      matchIn_X_YMWD = rg.in_X_YMWD.exec(input);
      matchEnd_of_WM = rg.end_of_WM.exec(input);
      
      if (!matchOn_M_Xst_Y)
      {
         matchFullDate = rg.fullDate.exec(input)
      }
      matchOn_Xst_of_M_Y = rg.on_Xst_of_M_Y.exec(input);
      if (!matchOn_Xst_of_M_Y)
      {
         if (!matchOn_M_Xst_Y && (!matchIn_X_YMWD && (!matchEnd_of_WM && !matchFullDate)))
         {
            matchOn_next_weekday = rg.on_next_weekday.exec(input)
         }
         matchOn_Xst = rg.on_Xst.exec(input)
      }
      matchNow = rg.reNow.exec(input);
      matchTom = rg.tom.exec(input)
   }
   var A9 = this.parseTimeSpec(input, true);
   var Ae = A9 !== null ? A9[1] : null;
   A9 = A9 !== null ? A9[0] : null;
   var Av = rg.time_sep.exec(input);
   var A4 = rg.time_hhmm.exec(input);
   var An = rg.time_hh.exec(input);
   var A6 = false;
   var O = false;
   if (!matchNow && (!matchTom && (!matchFullDate && (!matchOn_Xst_of_M_Y && (!matchOn_next_weekday && (!matchIn_X_YMWD && (!matchOn_Xst && (!matchOn_M_Xst_Y && (!matchRTMformat && (!matchEnd_of_WM && (!Av && (!A4 && (!An && !A9)))))))))))))
   {
      return this.useCache ? (this.cache[Q] = [null, null]) : [null, null]
   }
   var AE = null;
   var BI = null;
   var BA = null;
   var Au = null;
   if (matchRTMformat)
   {
      BA = input.indexOf(matchRTMformat[0]);
      Au = matchRTMformat[0].length;
      var BF = BA + matchRTMformat[0].length;
      var input_len = input.length;
      input = input.substring(BF, input_len);
      AE = AI;
      BI = matchRTMformat[0]
   }
   else
   {
      if (matchFullDate)
      {
         BA = input.indexOf(matchFullDate[0]);
         Au = matchFullDate[0].length;
         var BF = BA + matchFullDate[0].length;
         var input_len = input.length;
         input = input.substring(BF, input_len);
         AE = AH;
         BI = matchFullDate[0]
      }
      else
      {
         if (matchOn_Xst_of_M_Y)
         {
            BA = input.indexOf(matchOn_Xst_of_M_Y[0]);
            Au = matchOn_Xst_of_M_Y[0].length;
            var BF = BA + matchOn_Xst_of_M_Y[0].length;
            var input_len = input.length;
            input = input.substring(BF, input_len);
            AE = AG;
            BI = matchOn_Xst_of_M_Y[0]
         }
         else
         {
            if (matchIn_X_YMWD)
            {
               BA = input.indexOf(matchIn_X_YMWD[0]);
               Au = matchIn_X_YMWD[0].length;
               var BF = BA + matchIn_X_YMWD[0].length;
               var input_len = input.length;
               input = input.substring(BF, input_len);
               AE = matchEnd_of_WM;
               BI = matchIn_X_YMWD[0]
            }
            else
            {
               if (matchOn_Xst)
               {
                  BA = input.indexOf(matchOn_Xst[0]);
                  Au = matchOn_Xst[0].length;
                  var BF = BA + matchOn_Xst[0].length;
                  var input_len = input.length;
                  input = input.substring(BF, input_len);
                  AE = Ay;
                  BI = matchOn_Xst[0]
               }
               else
               {
                  if (matchOn_M_Xst_Y)
                  {
                     BA = input.indexOf(matchOn_M_Xst_Y[0]);
                     Au = matchOn_M_Xst_Y[0].length;
                     var BF = BA + matchOn_M_Xst_Y[0].length;
                     var input_len = input.length;
                     input = input.substring(BF, input_len);
                     AE = Aw;
                     BI = matchOn_M_Xst_Y[0]
                  }
                  else
                  {
                     if (matchEnd_of_WM)
                     {
                        BA = input.indexOf(matchEnd_of_WM[0]);
                        Au = matchEnd_of_WM[0].length;
                        var BF = BA + matchEnd_of_WM[0].length;
                        var input_len = input.length;
                        input = input.substring(BF, input_len);
                        AE = Ah;
                        BI = matchEnd_of_WM[0]
                     }
                  }
               }
            }
         }
      }
   }
   AS = BA;
   A9 = this.parseTimeSpec(input, true);
   Ae = A9 !== null ? A9[1] : null;
   A9 = A9 !== null ? A9[0] : null;
   Av = rg.time_sep.exec(input);
   A4 = rg.time_hhmm.exec(input);
   An = rg.time_hh.exec(input);
   var BG = null;
   var A0 = null;
   var A7 = null;
   var AA = null;
   var BM = null;
   var BH = true;
   var AX = false;
   var A8 = false;
   if (matchIn_X_YMWD)
   {
      var Y = input.split(/\s*(?:and|\,)\s*/);
      var in_X_YMWD_and_ = [];
      in_X_YMWD_and_.push(matchIn_X_YMWD);
      if (Y.length > 1)
      {
         for (var BK = 1; BK < Y.length; BK++)
         {
            in_X_YMWD_and_.push(rg.in_X_YMWD.exec(Y[BK]))
         }
      }
   }
   if (matchRTMformat && matchRTMformat[0])
   {
      if (matchRTMformat[2])
      {
         BG = this.getYear(matchRTMformat[2]);
         A6 = true
      }
      else
      {
         BG = I.getFullYear()
      }
      if (matchRTMformat[4])
      {
         A0 = matchRTMformat[4] * 1
      }
      else
      {
         A0 = I.getMonth() + 1
      }
      if (matchRTMformat[6])
      {
         A7 = matchRTMformat[6] * 1
      }
      else
      {
         A7 = 1
      }
   }
   else
   {
      if (matchFullDate)
      {
         if (matchFullDate[3])
         {
            BG = matchFullDate[1].length == 4 ? 1 : 3;
            A0 = BG == 1 ? 2 : configurationMgr.dateStyle === 1 ? 1 : 2;
            A7 = BG == 1 ? 3 : configurationMgr.dateStyle === 1 ? 2 : 1;
            A6 = true;
            if (matchFullDate[A0] > 12)
            {
               var BL = A0;
               A0 = A7;
               A7 = BL
            }
            BG = this.getYear(matchFullDate[BG]);
            A0 = matchFullDate[A0];
            A7 = matchFullDate[A7]
         }
         else
         {
            if (matchFullDate[1].length == 4)
            {
               BG = matchFullDate[1];
               A0 = matchFullDate[2];
               A7 = 1
            }
            else
            {
               BG = I.getFullYear();
               if (configurationMgr.dateStyle === 1)
               {
                  A0 = 1;
                  A7 = 2
               }
               else
               {
                  A0 = 2;
                  A7 = 1
               }
               if (matchFullDate[A0] > 12)
               {
                  var BL = A7;
                  A7 = A0;
                  A0 = BL
               }
               A0 = matchFullDate[A0];
               A7 = matchFullDate[A7]
            }
         }
      }
      else
      {
         if (matchOn_Xst_of_M_Y)
         {
            A0 = this.getMonth(matchOn_Xst_of_M_Y[4]);
            if (!matchOn_Xst_of_M_Y[3])
            {
               if (!matchOn_Xst_of_M_Y[5])
               {
                  A7 = 1
               }
               else
               {
                  A7 = matchOn_Xst_of_M_Y[5];
                  matchOn_Xst_of_M_Y[5] = null
               }
            }
            else
            {
               A7 = matchOn_Xst_of_M_Y[3]
            }
            if (matchOn_Xst_of_M_Y[5])
            {
               BG = this.getYear(matchOn_Xst_of_M_Y[5]);
               A6 = true
            }
            else
            {
               BG = I.getFullYear()
            }
         }
         else
         {
            if (matchOn_next_weekday)
            {
               AS = input.indexOf(matchOn_next_weekday[0]);
               Au = matchOn_next_weekday[0].length;
               var W = false;
               if (matchOn_next_weekday[3])
               {
                  W = true
               }
               if (matchOn_next_weekday[4])
               {
                  BG = I.getFullYear();
                  A0 = I.getMonth() + 1;
                  A7 = I.getDate() + this.calculateDate(this.getDay(matchOn_next_weekday[4]), I.getDay(), W)
               }
            }
            else
            {
               if (matchIn_X_YMWD)
               {
                  var Am = null;
                  for (var BK = 0; BK < in_X_YMWD_and_.length; BK++)
                  {
                     if (in_X_YMWD_and_[BK][2])
                     {
                        Am = this.calculateUnit(I, in_X_YMWD_and_[BK][2], in_X_YMWD_and_[BK][3], false)
                     }
                  }
                  if (Am)
                  {
                     AX = true;
                     BG = Am.getFullYear();
                     A0 = Am.getMonth() + 1;
                     A7 = Am.getDate()
                  }
                  else
                  {
                     return this.useCache ? (this.cache[Q] = [null, null]) : [null, null]
                  }
               }
               else
               {
                  if (matchTom)
                  {
                     AS = input.indexOf(matchTom[0]);
                     Au = matchTom[0].length;
                     var Am = null;
                     var V = matchTom[1].toLowerCase().substring(0, 3);
                     if (V == Af || V == Ad)
                     {
                        AX = true;
                        Am = this.calculateUnit(I, Aa, AY, false)
                     }
                     else
                     {
                        if (V == AW || V == AV)
                        {
                           Am = I
                        }
                        else
                        {
                           if (V == AT)
                           {
                              A8 = true;
                              Am = this.calculateUnit(I, Aa, AY, true)
                           }
                        }
                     }
                     if (Am)
                     {
                        BG = Am.getFullYear();
                        A0 = Am.getMonth() + 1;
                        A7 = Am.getDate()
                     }
                     else
                     {
                        return this.useCache ? (this.cache[Q] = [null, null]) : [null, null]
                     }
                  }
                  else
                  {
                     if (matchOn_Xst)
                     {
                        var A7 = parseInt(matchOn_Xst[2], 10);
                        var Am = new Date();
                        A0 = Am.getMonth();
                        if (A7 > 0 && A7 <= this.monthLength[A0])
                        {
                           BG = Am.getFullYear();
                           A0 = A0 + 1;
                           Am.setDate(A7)
                        }
                        else
                        {
                           return this.useCache ? (this.cache[Q] = [null, null]) : [null, null]
                        }
                     }
                     else
                     {
                        if (matchOn_M_Xst_Y)
                        {
                           A0 = this.getMonth(matchOn_M_Xst_Y[3]);
                           if (matchOn_M_Xst_Y[4].length == 4)
                           {
                              A7 = 1;
                              BG = this.getYear(matchOn_M_Xst_Y[4]);
                              A6 = true
                           }
                           else
                           {
                              A7 = parseInt(matchOn_M_Xst_Y[4], 10);
                              if (matchOn_M_Xst_Y[5])
                              {
                                 BG = this.getYear(matchOn_M_Xst_Y[5]);
                                 A6 = true
                              }
                              else
                              {
                                 BG = I.getFullYear()
                              }
                           }
                        }
                        else
                        {
                           if (matchEnd_of_WM)
                           {
                              BG = I.getFullYear();
                              A0 = I.getMonth() + 1;
                              if (matchEnd_of_WM[1] == AR)
                              {
                                 A7 = I.getDate() + this.calculateDate(this.getDay(AQ), I.getDay(), false)
                              }
                              else
                              {
                                 A7 = Date.days_in_month(BG, A0)
                              }
                           }
                           else
                           {
                              if (matchNow)
                              {
                                 var Am = new Date();
                                 BG = Am.getFullYear();
                                 A0 = Am.getMonth() + 1;
                                 A7 = Am.getDate();
                                 AA = Am.getHours();
                                 BM = Am.getMinutes()
                              }
                              else
                              {
                                 var Am = new Date();
                                 BG = Am.getFullYear();
                                 A0 = Am.getMonth() + 1;
                                 A7 = Am.getDate();
                                 BH = false
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
   var Ar = true;
   var M = null;
   var BJ = null;
   if (Av)
   {
      if (Av[2])
      {
         AA = Av[2];
         if (Av[3])
         {
            BM = parseInt(Av[3], 10)
         }
         else
         {
            BM = 0
         }
         AA = this.normalizeAMPM(AA, Av[4]);
         O = true;
         AE += AN;
         M = Q.indexOf(Av[0])
      }
   }
   else
   {
      if (A4)
      {
         if (A4[2])
         {
            if (A4[2].length == 3)
            {
               AA = parseInt(A4[2].substring(0, 1), 10);
               BM = parseInt(A4[2].substring(1, 3), 10)
            }
            else
            {
               AA = parseInt(A4[2].substring(0, 2), 10);
               BM = parseInt(A4[2].substring(2, 4), 10)
            }
            AA = this.normalizeAMPM(AA, A4[3]);
            O = true;
            AE += AM;
            M = Q.indexOf(A4[0])
         }
      }
      else
      {
         if (A9 === null && An)
         {
            if (An[2])
            {
               AA = parseInt(An[2], 10);
               BM = 0;
               AA = this.normalizeAMPM(AA, An[3]);
               O = true;
               AE += AL;
               M = Q.indexOf(An[0]);
               BJ = An[0].length
            }
         }
         else
         {
            An = null
         }
      }
   }
   var Ag = false;
   if (!Av && (!A4 && !An) && A9 !== null)
   {
      var As = this.splitTimeSpec(A9);
      AA = As[0];
      BM = As[1];
      seconds = As[2];
      if (Am)
      {
         Am.setHours(Am.getHours() + AA);
         Am.setMinutes(Am.getMinutes() + BM);
         AA = Am.getHours();
         BM = Am.getMinutes()
      }
      Ar = false;
      AE += AK;
      Ag = true;
      M = Ae
   }
   if (M !== null && (AS === null || M < AS))
   {
      AS = M;
      Au = BJ
   }
   var Z = true;
   if (AA === null)
   {
      AA = 0;
      Z = false
   }
   if (BM === null)
   {
      BM = 0;
      Z = false
   }
   var X = new Date();
   var Ax = X.getFullYear();
   var R = X.getMonth() + 1;
   var Ao = X.getDate();
   var AU = X.getHours();
   var AC = X.getMinutes();
   var BB = AU * 60 + AC;
   var AF = parseInt(BG, 10);
   var BC = parseInt(A0, 10);
   var U = parseInt(A7, 10);
   var Al = parseInt(AA, 10) * 60 + parseInt(BM, 10);
   var AD = AF === Ax && (BC === R && Ao === U);
   A0 = parseInt(A0, 10);
   if (A8 === false && (AX == false && (A6 == false && A0 < R)))
   {
      BG = parseInt(BG, 10) + 1
   }
   if (AD === true && (BH == false && ((O == true || Ag == true) && Al < BB)))
   {
      A7 = parseInt(A7, 10) + 1
   }
   var matchIn_X_YMWD = new Date(BG, A0 - 1, A7, AA, BM, 0);
   var A3 = [matchIn_X_YMWD, Z, Ar, AE, BI, Q, input, AS, Au, An];
   return this.useCache ? (this.cache[Q] = A3) : A3
};
rg.prototype.getTimeTuple = function ()
{
   var F = new Date();
   var H = new Date();
   H.setHours(0, 0, 0, 0);
   var D = dateTimeMgr.checkDate(H);
   H.setDate(H.getDate() + 1);
   var B = this.checkDate(H);
   H.setDate(H.getDate() + 1);
   var K = this.checkDate(H);
   var E = parseInt(F.getTime() / 1000, 10);
   var A = parseInt(D.getTime() / 1000, 10);
   var I = parseInt(B.getTime() / 1000, 10);
   var J = parseInt(K.getTime() / 1000, 10);
   return [E, A, I, J]
};
rg.interval_re = new RegExp("((\\d+)\\s*year(?:s)?)?\\s*((\\d+)\\s*mon(?:s)?)?\\s*((\\d+)\\s*day(?:s)?)?\\s*((\\d{2}):(\\d{2}):(\\d{2}))?");
rg.week_re = new RegExp("((\\d+)\\s*week(?:s)?)");
rg.prototype.TransformDate = function (X)
{
   var AE = "",
      AP = " days",
      AI = " seconds",
      AD = ", ",
      AJ = "1 second",
      Y = "INTERFACE_DF_1_MONTH",
      U = "INTERFACE_DF_1_WEEK",
      AN = "INTERFACE_DF_1_YEAR",
      W = "INTERFACE_DF_NUM_MONTHS",
      Q = "INTERFACE_DF_NUM_WEEKS",
      AA = "INTERFACE_DF_NUM_YEARS",
      M = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_DAYS",
      AM = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_HOURS",
      AK = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_NUM_MINUTES",
      O = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_DAY",
      K = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_HOUR",
      AL = "INTERFACE_TASKS_LIST_DETAILS_TIME_ESTIMATE_ONE_MINUTE",
      AF = "ja",
      AH = "zh-CN",
      AG = "zh-TW";
   var X = X.toLowerCase();
   var AC = rg.week_re.exec(X);
   if (AC && AC[0])
   {
      X = X.replace(AC[1], AC[2] * 7 + AP)
   }
   var AB = rg.interval_re.exec(X);
   var I = AB[2];
   var R = AB[4];
   var J = AB[6];
   var Z = AB[8];
   var V = AB[9];
   var H = AB[10];
   var AO = [];
   if (exists(I))
   {
      I = I * 1;
      if (I)
      {
         if (I == 1)
         {
            AO.push(_T(AN))
         }
         else
         {
            AO.push(_T(AA, {
               NUM: I
            }))
         }
      }
   }
   if (exists(R))
   {
      R = R * 1;
      if (R)
      {
         if (R == 1)
         {
            AO.push(_T(Y))
         }
         else
         {
            AO.push(_T(W, {
               NUM: R
            }))
         }
      }
   }
   if (exists(J))
   {
      J = J * 1;
      if (J > 6)
      {
         var AQ = Math.floor(J / 7);
         J = J % 7;
         if (AQ == 1)
         {
            AO.push(_T(U))
         }
         else
         {
            AO.push(_T(Q, {
               NUM: AQ
            }))
         }
      }
      if (J)
      {
         if (J == 1)
         {
            AO.push(_T(O))
         }
         else
         {
            AO.push(_T(M, {
               NUM: J
            }))
         }
      }
   }
   if (exists(Z))
   {
      Z = Z * 1;
      if (Z)
      {
         if (Z == 1)
         {
            AO.push(_T(K))
         }
         else
         {
            AO.push(_T(AM, {
               NUM: Z
            }))
         }
      }
   }
   if (exists(V))
   {
      V = V * 1;
      if (V)
      {
         if (V == 1)
         {
            AO.push(_T(AL))
         }
         else
         {
            AO.push(_T(AK, {
               NUM: V
            }))
         }
      }
   }
   if (exists(H))
   {
      H = H * 1;
      if (H)
      {
         if (H == 1)
         {
            AO.push(AJ)
         }
         else
         {
            AO.push(H + AI)
         }
      }
   }
   if (rtmLanguage == AH || (rtmLanguage == AG || rtmLanguage == AF))
   {
      return AO.join(AE)
   }
   return AO.join(AD)
};