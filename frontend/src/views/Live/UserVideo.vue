<template>
    <div v-if="streamManager">
        <ov-video :stream-manager="streamManager"/>
        <div><p>{{ clientData }}</p></div>
    </div>
</template>

<script>
import OvVideo from './OvVideo';
export default {
	name: 'UserVideo',
	components: {
		OvVideo,
	},
	props: {
		streamManager: Object,
	},
	computed: {
		clientData () {
			console.log(this.streamManager.stream);
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},
	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			console.log(JSON.parse(connection.data.split("%/%")[0]));
			return JSON.parse(connection.data.split("%/%")[0]);
		},
	},
};
</script>