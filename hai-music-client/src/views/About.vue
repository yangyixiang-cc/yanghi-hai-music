<template>
    <div class="main_box">
        <div class="box">
            <div>暂 无</div>
        </div>
    </div>
</template>

<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'About'
}
</script>

<style lang="scss" scoped>
.main_box .box{
    width: 100%;
    height: 15rem;
    padding-top: .625rem;
    background-color: white;
    background-image: url("../assets/images/backimg3.jpg");
    background-repeat: no-repeat;
    background-size: cover;
}

.main_box .box div{
    width:98%;
    margin: auto;
    height: 10rem;
    line-height: 10rem;
    text-align: center;
    color: #000;
    font-size: .625rem;
    font-weight: bolder;
}
</style>