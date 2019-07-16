package org.yy.mongodb.orm.engine.entry;

import org.yy.mongodb.exception.StatementException;

/**
 * Mongodb operator type.
 * @see {@link http://docs.mongodb.org/manual/reference/operator}
 * @author yy
 */
public enum Operator {
  
  /**
   * Query and Projection Operators.
   */
  /* Comparison */
  $eq("$eq"),
  $gt("$gt"),
  $gte("$gte"),
  $in("$in"),
  $lt("$lt"),
  $lte("$lte"),
  $ne("$ne"),
  $nin("$nin"),
  /* Logical */
  $and("$and"),
  $not("$not"),
  $nor("$nor"),
  $or("$or"),  
  /* Element */
  $exists("$exists"),
  $type("$type"),
  /* Evaluation */
  $expr("$expr"),
  $jsonSchema("$jsonSchema"),
  $mod("$mod"),
  $regex("$regex"),
  $text("$text"),
  $where("$where"),
  /* Geospatial */
  $geoIntersects("$geoIntersects"),
  $geoWithin("$geoWithin"),
  $near("$near"),
  $nearSphere("$nearSphere"),
  /* Array */
  $all("$all"),
  $elemMatch("$elemMatch"),
  $size("$size"),
  /* Bitwise */
  $bitsAllClear("$bitsAllClear"),
  $bitsAllSet("$bitsAllSet"),
  $bitsAnyClear("$bitsAnyClear"),
  $bitsAnySet("$bitsAnySet"),
  /* Comments */
  $comment("$comment"),
  /* Projection Operators */
  $("$"),
  $meta("$meta"),
  $slice("$slice"),
  
  /**
   * Update Operators.
   */
  /* Fields */
  $currentDate("$currentDate"),
  $inc("$inc"),
  $min("$min"),
  $max("$max"),
  $mul("$mul"),
  $rename("$rename"),
  $set("$set"),
  $setOnInsert("$setOnInsert"),
  $unset("$unset"),
  /* Array */
  $array("$[]"),
  $identifier("^\\$\\[.+\\]$"), // $[<identifier>]
  $addToSet("$addToSet"),
  $pop("$pop"),
  $pull("$pull"),
  $push("$push"),
  $pullAll("$pullAll"),
  /* Modifiers */
  $each("$each"),
  $position("$position"),
  $sort("$sort"),
  /* Bitwise */
  $bit("$bit"),
  
  /**
   * Aggregation Pipeline Stages
   */
  /* Collection Stages */
  $addFields("$addFields"),
  $bucket("$bucket"),
  $bucketAuto("$bucketAuto"),
  $collStats("$collStats"),
  $count("$count"),
  $facet("$facet"),
  $geoNear("$geoNear"),
  $graphLookup("$graphLookup"),
  $group("$group"),
  $indexStats("$indexStats"),
  $limit("$limit"),
  $listSessions("$listSessions"),
  $lookup("$lookup"),
  $match("$match"),
  $out("$out"),
  $project("$project"),
  $redact("$redact"),
  $replaceRoot("$replaceRoot"),
  $sample("$sample"),
  $skip("$skip"),
  $sortByCount("$sortByCount"),
  $unwind("$unwind"),
  /* DB Stages */
  $currentOp("$currentOp"),
  $listLocalSessions("$listLocalSessions"),    
  
  /**
   * Aggregation Pipeline Operators 
   */
  $abs("$abs"),
  $add("$add"),
  $ceil("$ceil"),
  $divide("$divide"),
  $exp("$exp"),
  $floor("$floor"),
  $ln("$ln"),
  $log("$log"),
  $log10("$log10"),
  $multiply("$multiply"),  
  $pow("$pow"),
  $sqrt("$sqrt"),
  $subtract("$subtract"),
  $trunc("$trunc"),
  /* Array Expression Operators */
  $arrayElemAt("$arrayElemAt"),
  $arrayToObject("$arrayToObject"),
  $concatArrays("$concatArrays"),
  $filter("$filter"),
  $indexOfArray("$indexOfArray"),
  $isArray("$isArray"),
  $map("$map"),
  $objectToArray("$objectToArray"),
  $range("$range"),
  $reduce("$reduce"),
  $reverseArray("$reverseArray"),
  $zip("$zip"),
  /* Comparison Expression Operators */
  $cmp("$cmp"),
  /* Conditional Expression Operators */
  $cond("$"),
  $ifNull("$ifNull"),
  $switch("$switch"),
  /* Date Expression Operators */
  $dateFromParts("$dateFromParts"),
  $dateFromString("$dateFromString"),
  $dateToParts("$dateToParts"),
  $dateToString("$dateToString"),
  $dayOfMonth("$dayOfMonth"),
  $dayOfWeek("$dayOfWeek"),
  $dayOfYear("$dayOfYear"),
  $hour("$hour"),
  $isoDayOfWeek("$isoDayOfWeek"),
  $isoWeek("$isoWeek"),
  $isoWeekYear("$isoWeekYear"),
  $millisecond("$millisecond"),
  $minute("$minute"),
  $month("$month"),
  $second("$second"),
  $toDate("$toDate"),
  $week("$week"),
  $year("$year"),
  /* Literal Expression Operator */
  $literal("$literal"),
  /* Object Expression Operators */
  $mergeObjects("$mergeObjects"),
  /* Set Expression Operators */
  $allElementsTrue("$allElementsTrue"),
  $anyElementTrue("$anyElementTrue"),
  $setDifference("$setDifference"),
  $setEquals("$setEquals"),
  $setIntersection("$setIntersection"),
  $setIsSubset("$setIsSubset"),
  $setUnion("$setUnion"),
  /* String Expression Operators */
  $concat("$concat"),
  $indexOfBytes("$indexOfBytes"),
  $indexOfCP("$indexOfCP"),
  $ltrim("$ltrim"),
  $rtrim("$rtrim"),
  $split("$split"),
  $strLenBytes("$strLenBytes"),
  $strLenCP("$strLenCP"),
  $strcasecmp("$strcasecmp"),
  $substr("$substr"),
  $substrBytes("$substrBytes"),
  $substrCP("$substrCP"),
  $toLower("$toLower"),
  $toString("$toString"),
  $trim("$trim"),
  $toUpper("$toUpper"),
  /* Type Expression Operators */
  $convert("$convert"),
  $toBool("$toBool"),
  $toDecimal("$toDecimal"),
  $toDouble("$toDouble"),
  $toInt("$toInt"),
  $toLong("$toLong"),
  $toObjectId("$toObjectId"),
  /* Accumulators ($group) */
  $avg("$avg"),
  $first("$first"),
  $last("$last"),
  $stdDevPop("$stdDevPop"),
  $stdDevSamp("$stdDevSamp"),
  $sum("$sum"),
  /* Variable Expression Operators */
  $let("$let"),
  
  /**
   * Query Modifiers
   */
  /* Operators */
  $explain("$explain"),
  $hint("$hint"),
  $maxScan("$maxScan"),
  $maxTimeMS("$maxTimeMS"),
  $orderby("$orderby"),
  $query("$query"),
  $returnKey("$returnKey"),
  $showDiskLoc("$showDiskLoc"),
  /* Sort Order */
  $natural("$natural"),;
  
  private String opt;
  
  public static String formName(String name) {
    if(name == null) {
      return null;
    }
    
    try {
      return Operator.valueOf(name).getOpt();
    } catch (IllegalArgumentException ex) {
      if (name.equals($array.opt)) {
        return name;
      }
      
      if (name.matches($identifier.opt)) {
        return name;
      }
      throw new StatementException("Error operator const '"+name+"'.");
    }
  }
  
  private Operator(String opt) {
    this.opt = opt;
  }

  public String getOpt() {
    return opt;
  }
}
