<template>
  <div class="search">
    <input v-model="keyword" type="text" class="search-input" placeholder="输入城市名或者拼音" />
    <div class="search-content" ref="search" v-show="keyword">
      <ul>
        <li
          class="search-item border-bottom"
          v-for="item of list"
          :key="item.id"
          @click="handleCityClick(item.name)"
        >{{item.name}}</li>
        <li class="search-item border-bottom" v-show="hasNoData">没有找到匹配数据</li>
      </ul>
    </div>
  </div>
</template>

<script>
import BScroll from "better-scroll";
import { mapActions } from "vuex";
export default {
  name: "CitySearch",
  props: {
    cities: Object,
  },
  data() {
    return {
      keyword: "",
      list: [],
      timer: null,
    };
  },
  methods: {
    handleCityClick: function (city) {
      this.changeCity(city);
      this.$router.push("/");
    },
    ...mapActions(["changeCity"]),
  },
  watch: {
    keyword() {
      if (this.timer) {
        clearTimeout(this.timer);
      }
      if (!this.keyword) {
        this.list = [];
        return;
      }

      this.timer = setTimeout(() => {
        const result = [];
        for (let i in this.cities) {
          this.cities[i].forEach((value) => {
            if (
              value.spell.indexOf(this.keyword) > -1 ||
              value.name.indexOf(this.keyword) > -1
            ) {
              result.push(value);
            }
          });
        }
        this.list = result;
      }, 100);
    },
  },
  computed: {
    hasNoData() {
      return !this.list.length;
    },
  },
  mounted() {
    this.scroll = new BScroll(this.$refs.search);
  },
};
</script>

<style lang="stylus" scoped>
@import '~styles/varibles.styl';

.search {
  height: 0.72rem;
  background: $bgColor;
  padding: 0.1rem;

  .search-input {
    box-sizing: border-box;
    padding: 0.1rem;
    height: 0.62rem;
    width: 100%;
    line-height: 0.62rem;
    text-align: center;
    border-radius: 0.06rem;
    color: #666;
  }
}

.search-content {
  z-index: 1;
  overflow: hidden;
  position: absolute;
  top: 1.58rem;
  left: 0;
  right: 0;
  bottom: 0;
  background: #eee;

  .search-item {
    line-height: 0.62rem;
    padding-left: 0.2rem;
    color: #666;
    background: #fff;
  }
}
</style>