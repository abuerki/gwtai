function com_google_gwt_gwtai_demo_GwtAI(){
  var $intern_0 = '', $intern_23 = '" for "gwt:onLoadErrorFn"', $intern_21 = '" for "gwt:onPropertyErrorFn"', $intern_8 = '"><\/script>', $intern_10 = '/', $intern_7 = '<script id="', $intern_51 = "<script>com_google_gwt_gwtai_demo_GwtAI.onInjectionDone('com.google.gwt.gwtai.demo.GwtAI')<\/script>", $intern_18 = '=', $intern_39 = 'B80A7747380A0CB6B4F150ED8C6F2AA2.cache.html', $intern_20 = 'Bad handler "', $intern_35 = 'D0F376429B7CD5ADC1C558C01FDD4E3F.cache.html', $intern_36 = 'D3EDA7D485F5695B0D2F227C286367E7.cache.html', $intern_47 = 'DOMContentLoaded', $intern_37 = 'E2A5DF75F10ED9204380324E3C3F059B.cache.html', $intern_38 = 'F7A40B9D28B6022813EAED69BE7DF9B3.cache.html', $intern_46 = 'GwtAI.css', $intern_9 = 'SCRIPT', $intern_6 = '__gwt_marker_com.google.gwt.gwtai.demo.GwtAI', $intern_11 = 'base', $intern_13 = 'clear.cache.gif', $intern_1 = 'com.google.gwt.gwtai.demo.GwtAI', $intern_17 = 'content', $intern_30 = 'gecko', $intern_31 = 'gecko1_8', $intern_4 = 'gwt.hybrid', $intern_40 = 'gwt/standard/standard.css', $intern_22 = 'gwt:onLoadErrorFn', $intern_19 = 'gwt:onPropertyErrorFn', $intern_16 = 'gwt:property', $intern_45 = 'head', $intern_34 = 'hosted.html?com_google_gwt_gwtai_demo_GwtAI', $intern_44 = 'href', $intern_29 = 'ie6', $intern_48 = 'iframe', $intern_12 = 'img', $intern_49 = "javascript:''", $intern_41 = 'link', $intern_14 = 'meta', $intern_33 = 'moduleRequested', $intern_28 = 'msie', $intern_15 = 'name', $intern_25 = 'opera', $intern_50 = 'position:absolute;width:0;height:0;border:none', $intern_42 = 'rel', $intern_27 = 'safari', $intern_5 = 'selectionDone', $intern_3 = 'selectionStart', $intern_2 = 'startup', $intern_43 = 'stylesheet', $intern_32 = 'unknown', $intern_24 = 'user.agent', $intern_26 = 'webkit';
  var $wnd = window, $doc = document, external = $wnd.external, $stats = $wnd.__gwtstatsEvent?function(a, b, c, d){
    $wnd.__gwtstatsEvent(a, b, c, d);
  }
  :null, scriptsDone, loadDone, bodyDone, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc;
  $stats && $stats($intern_1, $intern_2, $intern_3, {millis:(new Date()).getTime()});
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    try {
      return external && (external.gwtOnLoad && $wnd.location.search.indexOf($intern_4) == -1);
    }
     catch (e) {
      return false;
    }
  }

  function maybeStartModule(){
    if (scriptsDone && loadDone) {
      var iframe = $doc.getElementById($intern_1);
      var frameWnd = iframe.contentWindow;
      frameWnd.__gwt_initHandlers = com_google_gwt_gwtai_demo_GwtAI.__gwt_initHandlers;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      com_google_gwt_gwtai_demo_GwtAI = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, $intern_1, base);
      $stats && $stats($intern_1, $intern_2, $intern_5, {millis:(new Date()).getTime()});
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = $intern_6, markerScript;
    $doc.write($intern_7 + markerId + $intern_8);
    markerScript = $doc.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != $intern_9) {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var eq = path.lastIndexOf($intern_10);
      return eq >= 0?path.substring(0, eq + 1):$intern_0;
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == $intern_0) {
      var baseElements = $doc.getElementsByTagName($intern_11);
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        var loc = $doc.location;
        var href = loc.href;
        base = getDirectoryOfFile(href.substr(0, href.length - loc.hash.length));
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc.createElement($intern_12);
      img.src = base + $intern_13;
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName($intern_14);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute($intern_15), content;
      if (name) {
        if (name == $intern_16) {
          content = meta.getAttribute($intern_17);
          if (content) {
            var value, eq = content.indexOf($intern_18);
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = $intern_0;
            }
            metaProps[name] = value;
          }
        }
         else if (name == $intern_19) {
          content = meta.getAttribute($intern_17);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_20 + content + $intern_21);
            }
          }
        }
         else if (name == $intern_22) {
          content = meta.getAttribute($intern_17);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_20 + content + $intern_23);
            }
          }
        }
      }
    }
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  providers[$intern_24] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf($intern_25) != -1) {
      return $intern_25;
    }
     else if (ua.indexOf($intern_26) != -1) {
      return $intern_27;
    }
     else if (ua.indexOf($intern_28) != -1) {
      var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 6000) {
          return $intern_29;
        }
      }
    }
     else if (ua.indexOf($intern_30) != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return $intern_31;
      }
      return $intern_30;
    }
    return $intern_32;
  }
  ;
  values[$intern_24] = {gecko:0, gecko1_8:1, ie6:2, opera:3, safari:4};
  com_google_gwt_gwtai_demo_GwtAI.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats($intern_1, $intern_2, $intern_33, {millis:(new Date()).getTime()});
    maybeStartModule();
  }
  ;
  com_google_gwt_gwtai_demo_GwtAI.onScriptLoad = function(){
    if (frameInjected) {
      loadDone = true;
      maybeStartModule();
    }
  }
  ;
  computeScriptBase();
  processMetas();
  var strongName;
  if (isHostedMode()) {
    strongName = $intern_34;
  }
   else {
    try {
      unflattenKeylistIntoAnswers([$intern_31], $intern_35);
      unflattenKeylistIntoAnswers([$intern_27], $intern_36);
      unflattenKeylistIntoAnswers([$intern_25], $intern_37);
      unflattenKeylistIntoAnswers([$intern_30], $intern_38);
      unflattenKeylistIntoAnswers([$intern_29], $intern_39);
      strongName = answers[computePropValue($intern_24)];
    }
     catch (e) {
      return;
    }
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      if (!__gwt_stylesLoaded[$intern_40]) {
        var l = $doc.createElement($intern_41);
        __gwt_stylesLoaded[$intern_40] = l;
        l.setAttribute($intern_42, $intern_43);
        l.setAttribute($intern_44, base + $intern_40);
        $doc.getElementsByTagName($intern_45)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_46]) {
        var l = $doc.createElement($intern_41);
        __gwt_stylesLoaded[$intern_46] = l;
        l.setAttribute($intern_42, $intern_43);
        l.setAttribute($intern_44, base + $intern_46);
        $doc.getElementsByTagName($intern_45)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_47, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  var frameInjected;
  function maybeInjectFrame(){
    if (!frameInjected) {
      frameInjected = true;
      var iframe = $doc.createElement($intern_48);
      iframe.src = $intern_49;
      iframe.id = $intern_1;
      iframe.style.cssText = $intern_50;
      iframe.tabIndex = -1;
      $doc.body.appendChild(iframe);
      iframe.contentWindow.location.replace(base + strongName);
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_47, function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc.readyState)) {
      maybeInjectFrame();
      onBodyDone();
    }
  }
  , 50);
  $doc.write($intern_51);
}

com_google_gwt_gwtai_demo_GwtAI.__gwt_initHandlers = function(resize, beforeunload, unload){
  var $wnd = window, oldOnResize = $wnd.onresize, oldOnBeforeUnload = $wnd.onbeforeunload, oldOnUnload = $wnd.onunload;
  $wnd.onresize = function(evt){
    try {
      resize();
    }
     finally {
      oldOnResize && oldOnResize(evt);
    }
  }
  ;
  $wnd.onbeforeunload = function(evt){
    var ret, oldRet;
    try {
      ret = beforeunload();
    }
     finally {
      oldRet = oldOnBeforeUnload && oldOnBeforeUnload(evt);
    }
    if (ret != null) {
      return ret;
    }
    if (oldRet != null) {
      return oldRet;
    }
  }
  ;
  $wnd.onunload = function(evt){
    try {
      unload();
    }
     finally {
      oldOnUnload && oldOnUnload(evt);
    }
  }
  ;
}
;
com_google_gwt_gwtai_demo_GwtAI();
