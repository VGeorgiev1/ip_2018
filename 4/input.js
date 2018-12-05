regExps = {
"exercise_1": /Internet|Words|Or/,
"exercise_2": /088[^80-]{7}/,
"exercise_3": /[^01]+/,
"exercise_4": /^[a-z][^@$]{2,17}$/,
"exercise_5": /^[0-9][0-9][0-9]$|^1(?:(?=5)50{2}|[0-4][0-9]+)$/,
"exercise_6": /class=('|")[A-z\s]+('|")/
};
cssSelectors = {
"exercise_1": "item > java",
"exercise_2": ".diffClass",
"exercise_3": "java > tag",
"exercise_4": "css > item:nth-child(3)",
"exercise_5": "tag > java.class1:nth-child(2)",
"exercise_6": "item.class2 > item, item.class1 > item",
"exercise_7": "#diffId2 > java:nth-child(2)",
"exercise_8": "#someId"
};