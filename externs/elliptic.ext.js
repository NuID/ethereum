/**********************************************************************
 * Extern for elliptic
 * Generated by http://jmmk.github.io/javascript-externs-generator
 **********************************************************************/
var elliptic = {
  "curve": {
    "base": {
      "BasePoint": function () {}
    },
    "edwards": {
      "super_": {
        "BasePoint": function () {}
      }
    },
    "mont": {
      "super_": {
        "BasePoint": function () {}
      }
    },
    "short": {
      "super_": {
        "BasePoint": function () {}
      }
    }
  },
  "curves": {
    "PresetCurve": function () {},
    "ed25519": {
      "curve": {},
      "g": {},
      "n": {}
    },
    "secp256k1": {
      "curve": {},
      "g": {},
      "n": {}
    }
  },
  "ec": function () {},
  "eddsa": function () {},
  "rand": {
    "Rand": function () {}
  },
  "utils": {
    "assert": {
      "equal": function () {}
    },
    "cachedProperty": function () {},
    "encode": function () {},
    "getJSF": function () {},
    "getNAF": function () {},
    "intFromLE": function () {},
    "parseBytes": function () {},
    "toArray": function () {},
    "toHex": function () {},
    "zero2": function () {}
  },
  "version": {}
};
elliptic.curve.base.prototype = {
  "_fixedNafMul": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "point": function () {},
  "validate": function () {}
};
elliptic.curve.base.BasePoint.prototype = {
  "_encode": function () {},
  "_getBeta": function () {},
  "_getDoubles": function () {},
  "_getNAFPoints": function () {},
  "_hasDoubles": function () {},
  "dblp": function () {},
  "encode": function () {},
  "encodeCompressed": function () {},
  "eq": function () {},
  "precompute": function () {},
  "validate": function () {}
};
elliptic.curve.edwards.prototype = {
  "_fixedNafMul": function () {},
  "_mulA": function () {},
  "_mulC": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "jpoint": function () {},
  "point": function () {},
  "pointFromJSON": function () {},
  "pointFromX": function () {},
  "pointFromY": function () {},
  "validate": function () {}
};
elliptic.curve.edwards.super_.prototype = {
  "_fixedNafMul": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "point": function () {},
  "validate": function () {}
};
elliptic.curve.edwards.super_.BasePoint.prototype = {
  "_encode": function () {},
  "_getBeta": function () {},
  "_getDoubles": function () {},
  "_getNAFPoints": function () {},
  "_hasDoubles": function () {},
  "dblp": function () {},
  "encode": function () {},
  "encodeCompressed": function () {},
  "eq": function () {},
  "precompute": function () {},
  "validate": function () {}
};
elliptic.curve.mont.prototype = {
  "_fixedNafMul": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "point": function () {},
  "pointFromJSON": function () {},
  "validate": function () {}
};
elliptic.curve.mont.super_.prototype = {
  "_fixedNafMul": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "point": function () {},
  "validate": function () {}
};
elliptic.curve.mont.super_.BasePoint.prototype = {
  "_encode": function () {},
  "_getBeta": function () {},
  "_getDoubles": function () {},
  "_getNAFPoints": function () {},
  "_hasDoubles": function () {},
  "dblp": function () {},
  "encode": function () {},
  "encodeCompressed": function () {},
  "eq": function () {},
  "precompute": function () {},
  "validate": function () {}
};
elliptic.curve.short.prototype = {
  "_endoSplit": function () {},
  "_endoWnafMulAdd": function () {},
  "_fixedNafMul": function () {},
  "_getEndoBasis": function () {},
  "_getEndoRoots": function () {},
  "_getEndomorphism": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "jpoint": function () {},
  "point": function () {},
  "pointFromJSON": function () {},
  "pointFromX": function () {},
  "validate": function () {}
};
elliptic.curve.short.super_.prototype = {
  "_fixedNafMul": function () {},
  "_wnafMul": function () {},
  "_wnafMulAdd": function () {},
  "decodePoint": function () {},
  "point": function () {},
  "validate": function () {}
};
elliptic.curve.short.super_.BasePoint.prototype = {
  "_encode": function () {},
  "_getBeta": function () {},
  "_getDoubles": function () {},
  "_getNAFPoints": function () {},
  "_hasDoubles": function () {},
  "dblp": function () {},
  "encode": function () {},
  "encodeCompressed": function () {},
  "eq": function () {},
  "precompute": function () {},
  "validate": function () {}
};
elliptic.curves.ed25519.curve.a.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.a.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.c.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.c.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.c2.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.c2.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.d.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.d.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.dd.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.dd.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.t.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.t.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.x.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.x.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.y.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.y.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.z.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.g.z.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.one.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.one.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.redN.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.redN.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.two.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.two.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.zero.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.curve.zero.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.a.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.a.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.c.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.c.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.c2.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.c2.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.d.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.d.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.dd.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.dd.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.one.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.one.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.redN.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.redN.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.two.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.two.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.zero.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.curve.zero.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.t.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.t.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.x.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.x.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.y.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.y.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.z.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.g.z.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.ed25519.hash.prototype = {
  "_digest": function () {},
  "_pad": function () {},
  "_update": function () {},
  "digest": function () {},
  "update": function () {}
};
elliptic.curves.ed25519.hash.super_.prototype = {
  "_pad": function () {},
  "digest": function () {},
  "update": function () {}
};
elliptic.curves.secp256k1.curve.a.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.a.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.b.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.b.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.endo.beta.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.endo.beta.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.g.x.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.g.x.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.g.y.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.g.y.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.one.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.one.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.redN.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.redN.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.tinv.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.tinv.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.two.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.two.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.zero.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.curve.zero.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.a.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.a.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.b.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.b.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.endo.beta.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.endo.beta.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.one.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.one.red.prime.constructor.super_.prototype = {
  "_tmp": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.red.prime.constructor.prototype = {
  "_tmp": function () {},
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.redN.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.redN.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.tinv.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.tinv.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.two.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.two.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.zero.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.curve.zero.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.x.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.x.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.y.red.prime.constructor.prototype = {
  "constructor": function () {},
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.g.y.red.prime.constructor.super_.prototype = {
  "imulK": function () {},
  "ireduce": function () {},
  "split": function () {}
};
elliptic.curves.secp256k1.hash.prototype = {
  "_digest": function () {},
  "_pad": function () {},
  "_update": function () {},
  "digest": function () {},
  "update": function () {}
};
elliptic.curves.secp256k1.hash.super_.prototype = {
  "_pad": function () {},
  "digest": function () {},
  "update": function () {}
};
elliptic.ec.prototype = {
  "_truncateToN": function () {},
  "genKeyPair": function () {},
  "getKeyRecoveryParam": function () {},
  "keyFromPrivate": function () {},
  "keyFromPublic": function () {},
  "keyPair": function () {},
  "recoverPubKey": function () {},
  "sign": function () {},
  "verify": function () {}
};
elliptic.eddsa.prototype = {
  "decodeInt": function () {},
  "decodePoint": function () {},
  "encodeInt": function () {},
  "encodePoint": function () {},
  "hashInt": function () {},
  "isPoint": function () {},
  "keyFromPublic": function () {},
  "keyFromSecret": function () {},
  "makeSignature": function () {},
  "sign": function () {},
  "verify": function() {}
};
/**********************************************************************
 * End Generated Extern for elliptic
/**********************************************************************/