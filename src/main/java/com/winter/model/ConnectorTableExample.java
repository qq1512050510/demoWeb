package com.winter.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectorTableExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConnectorTableExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andConidIsNull() {
            addCriterion("conId is null");
            return (Criteria) this;
        }

        public Criteria andConidIsNotNull() {
            addCriterion("conId is not null");
            return (Criteria) this;
        }

        public Criteria andConidEqualTo(String value) {
            addCriterion("conId =", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidNotEqualTo(String value) {
            addCriterion("conId <>", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidGreaterThan(String value) {
            addCriterion("conId >", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidGreaterThanOrEqualTo(String value) {
            addCriterion("conId >=", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidLessThan(String value) {
            addCriterion("conId <", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidLessThanOrEqualTo(String value) {
            addCriterion("conId <=", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidLike(String value) {
            addCriterion("conId like", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidNotLike(String value) {
            addCriterion("conId not like", value, "conid");
            return (Criteria) this;
        }

        public Criteria andConidIn(List<String> values) {
            addCriterion("conId in", values, "conid");
            return (Criteria) this;
        }

        public Criteria andConidNotIn(List<String> values) {
            addCriterion("conId not in", values, "conid");
            return (Criteria) this;
        }

        public Criteria andConidBetween(String value1, String value2) {
            addCriterion("conId between", value1, value2, "conid");
            return (Criteria) this;
        }

        public Criteria andConidNotBetween(String value1, String value2) {
            addCriterion("conId not between", value1, value2, "conid");
            return (Criteria) this;
        }

        public Criteria andConurlIsNull() {
            addCriterion("conUrl is null");
            return (Criteria) this;
        }

        public Criteria andConurlIsNotNull() {
            addCriterion("conUrl is not null");
            return (Criteria) this;
        }

        public Criteria andConurlEqualTo(String value) {
            addCriterion("conUrl =", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlNotEqualTo(String value) {
            addCriterion("conUrl <>", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlGreaterThan(String value) {
            addCriterion("conUrl >", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlGreaterThanOrEqualTo(String value) {
            addCriterion("conUrl >=", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlLessThan(String value) {
            addCriterion("conUrl <", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlLessThanOrEqualTo(String value) {
            addCriterion("conUrl <=", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlLike(String value) {
            addCriterion("conUrl like", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlNotLike(String value) {
            addCriterion("conUrl not like", value, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlIn(List<String> values) {
            addCriterion("conUrl in", values, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlNotIn(List<String> values) {
            addCriterion("conUrl not in", values, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlBetween(String value1, String value2) {
            addCriterion("conUrl between", value1, value2, "conurl");
            return (Criteria) this;
        }

        public Criteria andConurlNotBetween(String value1, String value2) {
            addCriterion("conUrl not between", value1, value2, "conurl");
            return (Criteria) this;
        }

        public Criteria andConusernameIsNull() {
            addCriterion("conUserName is null");
            return (Criteria) this;
        }

        public Criteria andConusernameIsNotNull() {
            addCriterion("conUserName is not null");
            return (Criteria) this;
        }

        public Criteria andConusernameEqualTo(String value) {
            addCriterion("conUserName =", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameNotEqualTo(String value) {
            addCriterion("conUserName <>", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameGreaterThan(String value) {
            addCriterion("conUserName >", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameGreaterThanOrEqualTo(String value) {
            addCriterion("conUserName >=", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameLessThan(String value) {
            addCriterion("conUserName <", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameLessThanOrEqualTo(String value) {
            addCriterion("conUserName <=", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameLike(String value) {
            addCriterion("conUserName like", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameNotLike(String value) {
            addCriterion("conUserName not like", value, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameIn(List<String> values) {
            addCriterion("conUserName in", values, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameNotIn(List<String> values) {
            addCriterion("conUserName not in", values, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameBetween(String value1, String value2) {
            addCriterion("conUserName between", value1, value2, "conusername");
            return (Criteria) this;
        }

        public Criteria andConusernameNotBetween(String value1, String value2) {
            addCriterion("conUserName not between", value1, value2, "conusername");
            return (Criteria) this;
        }

        public Criteria andConpasswordIsNull() {
            addCriterion("conPassword is null");
            return (Criteria) this;
        }

        public Criteria andConpasswordIsNotNull() {
            addCriterion("conPassword is not null");
            return (Criteria) this;
        }

        public Criteria andConpasswordEqualTo(String value) {
            addCriterion("conPassword =", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordNotEqualTo(String value) {
            addCriterion("conPassword <>", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordGreaterThan(String value) {
            addCriterion("conPassword >", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("conPassword >=", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordLessThan(String value) {
            addCriterion("conPassword <", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordLessThanOrEqualTo(String value) {
            addCriterion("conPassword <=", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordLike(String value) {
            addCriterion("conPassword like", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordNotLike(String value) {
            addCriterion("conPassword not like", value, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordIn(List<String> values) {
            addCriterion("conPassword in", values, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordNotIn(List<String> values) {
            addCriterion("conPassword not in", values, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordBetween(String value1, String value2) {
            addCriterion("conPassword between", value1, value2, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConpasswordNotBetween(String value1, String value2) {
            addCriterion("conPassword not between", value1, value2, "conpassword");
            return (Criteria) this;
        }

        public Criteria andConsqltypeIsNull() {
            addCriterion("conSqlType is null");
            return (Criteria) this;
        }

        public Criteria andConsqltypeIsNotNull() {
            addCriterion("conSqlType is not null");
            return (Criteria) this;
        }

        public Criteria andConsqltypeEqualTo(String value) {
            addCriterion("conSqlType =", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeNotEqualTo(String value) {
            addCriterion("conSqlType <>", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeGreaterThan(String value) {
            addCriterion("conSqlType >", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeGreaterThanOrEqualTo(String value) {
            addCriterion("conSqlType >=", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeLessThan(String value) {
            addCriterion("conSqlType <", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeLessThanOrEqualTo(String value) {
            addCriterion("conSqlType <=", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeLike(String value) {
            addCriterion("conSqlType like", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeNotLike(String value) {
            addCriterion("conSqlType not like", value, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeIn(List<String> values) {
            addCriterion("conSqlType in", values, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeNotIn(List<String> values) {
            addCriterion("conSqlType not in", values, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeBetween(String value1, String value2) {
            addCriterion("conSqlType between", value1, value2, "consqltype");
            return (Criteria) this;
        }

        public Criteria andConsqltypeNotBetween(String value1, String value2) {
            addCriterion("conSqlType not between", value1, value2, "consqltype");
            return (Criteria) this;
        }

        public Criteria andCondrivernameIsNull() {
            addCriterion("conDriverName is null");
            return (Criteria) this;
        }

        public Criteria andCondrivernameIsNotNull() {
            addCriterion("conDriverName is not null");
            return (Criteria) this;
        }

        public Criteria andCondrivernameEqualTo(String value) {
            addCriterion("conDriverName =", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameNotEqualTo(String value) {
            addCriterion("conDriverName <>", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameGreaterThan(String value) {
            addCriterion("conDriverName >", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameGreaterThanOrEqualTo(String value) {
            addCriterion("conDriverName >=", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameLessThan(String value) {
            addCriterion("conDriverName <", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameLessThanOrEqualTo(String value) {
            addCriterion("conDriverName <=", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameLike(String value) {
            addCriterion("conDriverName like", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameNotLike(String value) {
            addCriterion("conDriverName not like", value, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameIn(List<String> values) {
            addCriterion("conDriverName in", values, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameNotIn(List<String> values) {
            addCriterion("conDriverName not in", values, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameBetween(String value1, String value2) {
            addCriterion("conDriverName between", value1, value2, "condrivername");
            return (Criteria) this;
        }

        public Criteria andCondrivernameNotBetween(String value1, String value2) {
            addCriterion("conDriverName not between", value1, value2, "condrivername");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}