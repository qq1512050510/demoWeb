package com.winter.model;

import java.util.ArrayList;
import java.util.List;

public class SqlTableExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SqlTableExample() {
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

        public Criteria andSqlidIsNull() {
            addCriterion("sqlId is null");
            return (Criteria) this;
        }

        public Criteria andSqlidIsNotNull() {
            addCriterion("sqlId is not null");
            return (Criteria) this;
        }

        public Criteria andSqlidEqualTo(String value) {
            addCriterion("sqlId =", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidNotEqualTo(String value) {
            addCriterion("sqlId <>", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidGreaterThan(String value) {
            addCriterion("sqlId >", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidGreaterThanOrEqualTo(String value) {
            addCriterion("sqlId >=", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidLessThan(String value) {
            addCriterion("sqlId <", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidLessThanOrEqualTo(String value) {
            addCriterion("sqlId <=", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidLike(String value) {
            addCriterion("sqlId like", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidNotLike(String value) {
            addCriterion("sqlId not like", value, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidIn(List<String> values) {
            addCriterion("sqlId in", values, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidNotIn(List<String> values) {
            addCriterion("sqlId not in", values, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidBetween(String value1, String value2) {
            addCriterion("sqlId between", value1, value2, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlidNotBetween(String value1, String value2) {
            addCriterion("sqlId not between", value1, value2, "sqlid");
            return (Criteria) this;
        }

        public Criteria andSqlcontentIsNull() {
            addCriterion("sqlContent is null");
            return (Criteria) this;
        }

        public Criteria andSqlcontentIsNotNull() {
            addCriterion("sqlContent is not null");
            return (Criteria) this;
        }

        public Criteria andSqlcontentEqualTo(String value) {
            addCriterion("sqlContent =", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentNotEqualTo(String value) {
            addCriterion("sqlContent <>", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentGreaterThan(String value) {
            addCriterion("sqlContent >", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentGreaterThanOrEqualTo(String value) {
            addCriterion("sqlContent >=", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentLessThan(String value) {
            addCriterion("sqlContent <", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentLessThanOrEqualTo(String value) {
            addCriterion("sqlContent <=", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentLike(String value) {
            addCriterion("sqlContent like", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentNotLike(String value) {
            addCriterion("sqlContent not like", value, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentIn(List<String> values) {
            addCriterion("sqlContent in", values, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentNotIn(List<String> values) {
            addCriterion("sqlContent not in", values, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentBetween(String value1, String value2) {
            addCriterion("sqlContent between", value1, value2, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqlcontentNotBetween(String value1, String value2) {
            addCriterion("sqlContent not between", value1, value2, "sqlcontent");
            return (Criteria) this;
        }

        public Criteria andSqldetailsIsNull() {
            addCriterion("sqlDetails is null");
            return (Criteria) this;
        }

        public Criteria andSqldetailsIsNotNull() {
            addCriterion("sqlDetails is not null");
            return (Criteria) this;
        }

        public Criteria andSqldetailsEqualTo(String value) {
            addCriterion("sqlDetails =", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsNotEqualTo(String value) {
            addCriterion("sqlDetails <>", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsGreaterThan(String value) {
            addCriterion("sqlDetails >", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsGreaterThanOrEqualTo(String value) {
            addCriterion("sqlDetails >=", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsLessThan(String value) {
            addCriterion("sqlDetails <", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsLessThanOrEqualTo(String value) {
            addCriterion("sqlDetails <=", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsLike(String value) {
            addCriterion("sqlDetails like", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsNotLike(String value) {
            addCriterion("sqlDetails not like", value, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsIn(List<String> values) {
            addCriterion("sqlDetails in", values, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsNotIn(List<String> values) {
            addCriterion("sqlDetails not in", values, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsBetween(String value1, String value2) {
            addCriterion("sqlDetails between", value1, value2, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andSqldetailsNotBetween(String value1, String value2) {
            addCriterion("sqlDetails not between", value1, value2, "sqldetails");
            return (Criteria) this;
        }

        public Criteria andTSortSortidIsNull() {
            addCriterion("t_sort_sortId is null");
            return (Criteria) this;
        }

        public Criteria andTSortSortidIsNotNull() {
            addCriterion("t_sort_sortId is not null");
            return (Criteria) this;
        }

        public Criteria andTSortSortidEqualTo(Integer value) {
            addCriterion("t_sort_sortId =", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidNotEqualTo(Integer value) {
            addCriterion("t_sort_sortId <>", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidGreaterThan(Integer value) {
            addCriterion("t_sort_sortId >", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidGreaterThanOrEqualTo(Integer value) {
            addCriterion("t_sort_sortId >=", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidLessThan(Integer value) {
            addCriterion("t_sort_sortId <", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidLessThanOrEqualTo(Integer value) {
            addCriterion("t_sort_sortId <=", value, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidIn(List<Integer> values) {
            addCriterion("t_sort_sortId in", values, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidNotIn(List<Integer> values) {
            addCriterion("t_sort_sortId not in", values, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidBetween(Integer value1, Integer value2) {
            addCriterion("t_sort_sortId between", value1, value2, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andTSortSortidNotBetween(Integer value1, Integer value2) {
            addCriterion("t_sort_sortId not between", value1, value2, "tSortSortid");
            return (Criteria) this;
        }

        public Criteria andSqlsortIsNull() {
            addCriterion("sqlSort is null");
            return (Criteria) this;
        }

        public Criteria andSqlsortIsNotNull() {
            addCriterion("sqlSort is not null");
            return (Criteria) this;
        }

        public Criteria andSqlsortEqualTo(String value) {
            addCriterion("sqlSort =", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortNotEqualTo(String value) {
            addCriterion("sqlSort <>", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortGreaterThan(String value) {
            addCriterion("sqlSort >", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortGreaterThanOrEqualTo(String value) {
            addCriterion("sqlSort >=", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortLessThan(String value) {
            addCriterion("sqlSort <", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortLessThanOrEqualTo(String value) {
            addCriterion("sqlSort <=", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortLike(String value) {
            addCriterion("sqlSort like", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortNotLike(String value) {
            addCriterion("sqlSort not like", value, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortIn(List<String> values) {
            addCriterion("sqlSort in", values, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortNotIn(List<String> values) {
            addCriterion("sqlSort not in", values, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortBetween(String value1, String value2) {
            addCriterion("sqlSort between", value1, value2, "sqlsort");
            return (Criteria) this;
        }

        public Criteria andSqlsortNotBetween(String value1, String value2) {
            addCriterion("sqlSort not between", value1, value2, "sqlsort");
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