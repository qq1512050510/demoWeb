package com.winter.model;

import java.util.ArrayList;
import java.util.List;

public class SqlConnectorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SqlConnectorExample() {
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

        public Criteria andSqlstateIsNull() {
            addCriterion("sqlState is null");
            return (Criteria) this;
        }

        public Criteria andSqlstateIsNotNull() {
            addCriterion("sqlState is not null");
            return (Criteria) this;
        }

        public Criteria andSqlstateEqualTo(String value) {
            addCriterion("sqlState =", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateNotEqualTo(String value) {
            addCriterion("sqlState <>", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateGreaterThan(String value) {
            addCriterion("sqlState >", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateGreaterThanOrEqualTo(String value) {
            addCriterion("sqlState >=", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateLessThan(String value) {
            addCriterion("sqlState <", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateLessThanOrEqualTo(String value) {
            addCriterion("sqlState <=", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateLike(String value) {
            addCriterion("sqlState like", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateNotLike(String value) {
            addCriterion("sqlState not like", value, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateIn(List<String> values) {
            addCriterion("sqlState in", values, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateNotIn(List<String> values) {
            addCriterion("sqlState not in", values, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateBetween(String value1, String value2) {
            addCriterion("sqlState between", value1, value2, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqlstateNotBetween(String value1, String value2) {
            addCriterion("sqlState not between", value1, value2, "sqlstate");
            return (Criteria) this;
        }

        public Criteria andSqldialetIsNull() {
            addCriterion("sqlDialet is null");
            return (Criteria) this;
        }

        public Criteria andSqldialetIsNotNull() {
            addCriterion("sqlDialet is not null");
            return (Criteria) this;
        }

        public Criteria andSqldialetEqualTo(String value) {
            addCriterion("sqlDialet =", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetNotEqualTo(String value) {
            addCriterion("sqlDialet <>", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetGreaterThan(String value) {
            addCriterion("sqlDialet >", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetGreaterThanOrEqualTo(String value) {
            addCriterion("sqlDialet >=", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetLessThan(String value) {
            addCriterion("sqlDialet <", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetLessThanOrEqualTo(String value) {
            addCriterion("sqlDialet <=", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetLike(String value) {
            addCriterion("sqlDialet like", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetNotLike(String value) {
            addCriterion("sqlDialet not like", value, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetIn(List<String> values) {
            addCriterion("sqlDialet in", values, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetNotIn(List<String> values) {
            addCriterion("sqlDialet not in", values, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetBetween(String value1, String value2) {
            addCriterion("sqlDialet between", value1, value2, "sqldialet");
            return (Criteria) this;
        }

        public Criteria andSqldialetNotBetween(String value1, String value2) {
            addCriterion("sqlDialet not between", value1, value2, "sqldialet");
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