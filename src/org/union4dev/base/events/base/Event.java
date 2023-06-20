package org.union4dev.base.events.base;

/**
 * The most basic form of an event. You have to implement this interface in
 * order for the EventAPI to recognize the event.
 *
 * @author DarkMagician6
 * @since July 30, 2013
 */
public interface Event {

    /**
     * The most basic form of an stoppable Event. Stoppable events are called
     * seperate from other events and the calling of methods is stopped as soon as
     * the EventStoppable is stopped.
     *
     * @author DarkMagician6
     * @since 26-9-13
     */
    abstract class EventStoppable implements Event {

        private boolean stopped;

        /**
         * No need for the constructor to be public.
         */
        protected EventStoppable() {
        }

        /**
         * Sets the stopped state to true.
         */
        public void stop() {
            stopped = true;
        }

        /**
         * Checks the stopped boolean.
         *
         * @return True if the EventStoppable is stopped.
         */
        public boolean isStopped() {
            return stopped;
        }

    }

    /**
     * Abstract example implementation of the Cancellable interface.
     *
     * @author DarkMagician6
     * @since August 27, 2013
     */
    abstract class EventCancellable implements Event, Cancellable {
        public boolean cancelled;

        protected EventCancellable() {
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean state) {
            cancelled = state;
        }

    }
}
