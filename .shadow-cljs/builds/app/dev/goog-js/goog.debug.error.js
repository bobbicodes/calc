["^ ","~:resource-id",["~:shadow.build.classpath/resource","goog/debug/error.js"],"~:js","goog.loadModule(function(exports) {\n  \"use strict\";\n  goog.module(\"goog.debug.Error\");\n  goog.module.declareLegacyNamespace();\n  function DebugError(opt_msg) {\n    if (Error.captureStackTrace) {\n      Error.captureStackTrace(this, DebugError);\n    } else {\n      const stack = (new Error).stack;\n      if (stack) {\n        this.stack = stack;\n      }\n    }\n    if (opt_msg) {\n      this.message = String(opt_msg);\n    }\n    this.reportErrorToServer = true;\n  }\n  goog.inherits(DebugError, Error);\n  DebugError.prototype.name = \"CustomError\";\n  exports = DebugError;\n  return exports;\n});\n","~:source","/**\n * @license\n * Copyright The Closure Library Authors.\n * SPDX-License-Identifier: Apache-2.0\n */\n\n/**\n * @fileoverview Provides a base class for custom Error objects such that the\n * stack is correctly maintained.\n *\n * You should never need to throw DebugError(msg) directly, Error(msg) is\n * sufficient.\n */\n\ngoog.module('goog.debug.Error');\ngoog.module.declareLegacyNamespace();\n\n\n\n/**\n * Base class for custom error objects.\n * @param {*=} opt_msg The message associated with the error.\n * @constructor\n * @extends {Error}\n */\nfunction DebugError(opt_msg) {\n  // Attempt to ensure there is a stack trace.\n  if (Error.captureStackTrace) {\n    Error.captureStackTrace(this, DebugError);\n  } else {\n    const stack = new Error().stack;\n    if (stack) {\n      /** @override */\n      this.stack = stack;\n    }\n  }\n\n  if (opt_msg) {\n    /** @override */\n    this.message = String(opt_msg);\n  }\n\n  /**\n   * Whether to report this error to the server. Setting this to false will\n   * cause the error reporter to not report the error back to the server,\n   * which can be useful if the client knows that the error has already been\n   * logged on the server.\n   * @type {boolean}\n   */\n  this.reportErrorToServer = true;\n}\ngoog.inherits(DebugError, Error);\n\n\n/** @override */\nDebugError.prototype.name = 'CustomError';\n\n\nexports = DebugError;\n","~:compiled-at",1624255573475,"~:source-map-json","{\n\"version\":3,\n\"file\":\"goog.debug.error.js\",\n\"lineCount\":24,\n\"mappings\":\"AAcA,IAAA,CAAA,UAAA,CAAA,QAAA,CAAA,OAAA,CAAA;AAAA,cAAA;AAAAA,MAAKC,CAAAA,MAAL,CAAY,kBAAZ,CAAA;AACAD,MAAKC,CAAAA,MAAOC,CAAAA,sBAAZ,EAAA;AAUAC,UAASA,WAAU,CAACC,OAAD,CAAU;AAE3B,QAAIC,KAAMC,CAAAA,iBAAV;AACED,WAAMC,CAAAA,iBAAN,CAAwB,IAAxB,EAA8BH,UAA9B,CAAA;AADF,UAEO;AACL,YAAMI,QAAoBA,CAAZ,IAAIF,KAAQE,EAAAA,KAA1B;AACA,UAAIA,KAAJ;AAEE,YAAKA,CAAAA,KAAL,GAAaA,KAAb;AAFF;AAFK;AAQP,QAAIH,OAAJ;AAEE,UAAKI,CAAAA,OAAL,GAAeC,MAAA,CAAOL,OAAP,CAAf;AAFF;AAYA,QAAKM,CAAAA,mBAAL,GAA2B,IAA3B;AAxB2B;AA0B7BV,MAAKW,CAAAA,QAAL,CAAcR,UAAd,EAA0BE,KAA1B,CAAA;AAIAF,YAAWS,CAAAA,SAAUC,CAAAA,IAArB,GAA4B,aAA5B;AAGAC,SAAA,GAAUX,UAAV;AA5CA,SAAA,OAAA;AAAA,CAAA,CAAA;;\",\n\"sources\":[\"goog/debug/error.js\"],\n\"sourcesContent\":[\"/**\\n * @license\\n * Copyright The Closure Library Authors.\\n * SPDX-License-Identifier: Apache-2.0\\n */\\n\\n/**\\n * @fileoverview Provides a base class for custom Error objects such that the\\n * stack is correctly maintained.\\n *\\n * You should never need to throw DebugError(msg) directly, Error(msg) is\\n * sufficient.\\n */\\n\\ngoog.module('goog.debug.Error');\\ngoog.module.declareLegacyNamespace();\\n\\n\\n\\n/**\\n * Base class for custom error objects.\\n * @param {*=} opt_msg The message associated with the error.\\n * @constructor\\n * @extends {Error}\\n */\\nfunction DebugError(opt_msg) {\\n  // Attempt to ensure there is a stack trace.\\n  if (Error.captureStackTrace) {\\n    Error.captureStackTrace(this, DebugError);\\n  } else {\\n    const stack = new Error().stack;\\n    if (stack) {\\n      /** @override */\\n      this.stack = stack;\\n    }\\n  }\\n\\n  if (opt_msg) {\\n    /** @override */\\n    this.message = String(opt_msg);\\n  }\\n\\n  /**\\n   * Whether to report this error to the server. Setting this to false will\\n   * cause the error reporter to not report the error back to the server,\\n   * which can be useful if the client knows that the error has already been\\n   * logged on the server.\\n   * @type {boolean}\\n   */\\n  this.reportErrorToServer = true;\\n}\\ngoog.inherits(DebugError, Error);\\n\\n\\n/** @override */\\nDebugError.prototype.name = 'CustomError';\\n\\n\\nexports = DebugError;\\n\"],\n\"names\":[\"goog\",\"module\",\"declareLegacyNamespace\",\"DebugError\",\"opt_msg\",\"Error\",\"captureStackTrace\",\"stack\",\"message\",\"String\",\"reportErrorToServer\",\"inherits\",\"prototype\",\"name\",\"exports\"]\n}\n"]