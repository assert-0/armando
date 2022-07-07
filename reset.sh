#!/bin/bash

ids=()

launch_consumers() {
    for i in {0..10}
    do
        forge consume_message -t $1 -g $2 &
        ids+=($!)
    done
}

kill_consumers() {
    for id_ in "${ids[@]}"
    do
        kill $id_
    done
    exit
}

trap kill_consumers EXIT

launch_consumers armando-telegram_adapter-input telegram_adapter
launch_consumers armando-rule_engine-input rule_engine
launch_consumers armando-gpt3_adapter-input gpt3_adapter
launch_consumers armando-db_adapter-input db_adapter

sleep 10
