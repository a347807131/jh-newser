<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="tops">
                <h2 class="jh-entity-heading"><span v-text="$t('jhdApp.tops.detail.title')">Tops</span> {{tops.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhdApp.tops.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{tops.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.tops.link')">Link</span>
                    </dt>
                    <dd>
                        <span>{{tops.link}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.tops.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{tops.source}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.tops.time')">Time</span>
                    </dt>
                    <dd>
                        <span>{{tops.time}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.tops.seq')">Seq</span>
                    </dt>
                    <dd>
                        <span>{{tops.seq}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.tops.note')">Note</span>
                    </dt>
                    <dd>
                        <span>{{tops.note}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="tops.id" :to="{name: 'TopsEdit', params: {topsId: tops.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tops-details.component.ts">
</script>
